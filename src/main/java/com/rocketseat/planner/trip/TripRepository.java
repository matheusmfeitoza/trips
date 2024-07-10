package com.rocketseat.planner.trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Repository = Comunicacao com banco de dados
public interface TripRepository extends JpaRepository<Trip, UUID> {
}
