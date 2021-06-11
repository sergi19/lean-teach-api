package io.leantech.demo.config;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.leantech.demo.model.Position;
import io.leantech.demo.repository.PositionRepository;

@Configuration
public class PositionConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(final PositionRepository positionRepository) {
		return (args) -> {
			Position dev = new Position(1L, "dev");
			Position qa = new Position(2L, "qa");
			positionRepository.saveAll(Arrays.asList(dev, qa));
		};
	}
	
}
