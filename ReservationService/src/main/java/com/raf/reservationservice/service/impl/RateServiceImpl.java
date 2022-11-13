package com.raf.reservationservice.service.impl;

import com.raf.reservationservice.domain.Rate;
import com.raf.reservationservice.dto.RateCreateDto;
import com.raf.reservationservice.dto.RateDto;
import com.raf.reservationservice.dto.ReservationDto;
import com.raf.reservationservice.dto.UserDto;
import com.raf.reservationservice.exception.BadRequestException;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.mapper.RateMapper;
import com.raf.reservationservice.repository.RateRepository;
import com.raf.reservationservice.service.RateService;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RateServiceImpl implements RateService {

    private RateRepository rateRepository;
    private RestTemplate userServiceApiClient;
    private RateMapper rateMapper;
    private Retry userServiceRetry;


    public RateServiceImpl(RateRepository rateRepository, @Qualifier("userServiceApiClient") RestTemplate userServiceApiClient,
                            RateMapper rateMapper, Retry userServiceRetry) {
        this.rateRepository = rateRepository;
        this.userServiceApiClient = userServiceApiClient;
        this.rateMapper = rateMapper;
        this.userServiceRetry = userServiceRetry;
    }

    @Override
    public RateDto createRate(RateCreateDto rateCreateDto) {
        Optional<Rate> rate = rateRepository.findRateByClientIdAndHotelId(rateCreateDto.getClientId(), rateCreateDto.getHotelId());
        if(rate.isPresent()){
            throw new BadRequestException("Rate with given client and hotel already exist");
        }

            UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(rateCreateDto.getClientId())).get();

            Rate newRate = rateMapper.rateCreateDtoToRate(rateCreateDto);
            rateRepository.save(newRate);

            return rateMapper.rateToRateDto(newRate, userDto);
    }

    @Override
    public RateDto updateRate(RateCreateDto rateCreateDto, Long id) {
        Optional<Rate> rateCheck = rateRepository.findById(id);
        if(!rateCheck.isPresent()){
            throw new NotFoundException("Rate with given id doesn't exist");
        }
            UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(rateCreateDto.getClientId())).get();

            Rate rate = rateMapper.updateRate(rateCreateDto, rateCheck.get());

            rateRepository.save(rate);

            return rateMapper.rateToRateDto(rate, userDto);


    }

    @Override
    public void deleteRate(Long id) {
        Optional<Rate> rateCheck = rateRepository.findById(id);
        if(!rateCheck.isPresent()){
            throw new NotFoundException("Rate with given id doesn't exist");
        }
        rateRepository.delete(rateCheck.get());
    }

    @Override
    public RateDto findRateById(Long id) {
        Optional<Rate> rateCheck = rateRepository.findById(id);
        if(!rateCheck.isPresent()){
            throw new NotFoundException("Rate with given id doesn't exist");
        }

        UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(rateCheck.get().getClientId())).get();

        return rateMapper.rateToRateDto(rateCheck.get(), userDto);

    }

    @Override
    public Page<RateDto> findAllRates(Pageable pageable) {
        List<Rate> rateList = rateRepository.findAll();
        List<RateDto> rateDtos = new ArrayList<>();
        for(Rate r: rateList){

                UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(r.getClientId())).get();
                rateDtos.add(rateMapper.rateToRateDto(r, userDto));
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), rateDtos.size());
        final Page<RateDto> page = new PageImpl<>(rateDtos.subList(start, end), pageable, rateDtos.size());
        return page;
    }
    private UserDto getUserDto(Long id){
        System.out.println("Getting user...");
        try {
            ResponseEntity<UserDto> userDto = userServiceApiClient.exchange("/" + id, HttpMethod.GET, null, UserDto.class);
            return userDto.getBody();
        }catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 404)
                throw new NotFoundException("User with given id doesn't exist");
            throw  new RuntimeException("Internal server error!");
        }catch (Exception e){
            throw  new RuntimeException("Internal server error!");
        }

    }
}
