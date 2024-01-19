package com.kotkina.services;

import com.kotkina.errors.NoSuchTicketException;
import com.kotkina.models.Ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketStatistics {

    public Map<String, Long> getMinFlightTime(List<Ticket> tickets, String origin, String destination) {
        checkParams(tickets, origin, destination);

        Map<String, Long> flightMap = tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination))
                .collect(Collectors.toMap(
                        Ticket::getCarrier, ticket -> getFlightTime(ticket), Math::min));
        if (flightMap.isEmpty()) {
            throw new NoSuchTicketException("Билеты с заданными параметрами не найдены.");
        }
        return flightMap;
    }

    public double getAverageAndMedianPricesDifference(List<Ticket> tickets, String origin, String destination) {
        checkParams(tickets, origin, destination);

        double[] prices = tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination))
                .mapToDouble(Ticket::getPrice)
                .sorted()
                .toArray();
        if (prices.length == 0) {
            throw new NoSuchTicketException("Билеты с заданными параметрами не найдены.");
        }
        return getAveragePrice(prices) - getMedianPrice(prices);
    }

    private static long getFlightTime(Ticket ticket) {
        return LocalDateTime.of(ticket.getDeparture_date(), ticket.getDeparture_time())
                .until(LocalDateTime.of(ticket.getArrival_date(), ticket.getArrival_time()), ChronoUnit.MINUTES);
    }

    private double getAveragePrice(double[] prices) {
        return Math.round(Arrays.stream(prices)
                .average()
                .getAsDouble() * 100) / 100.0;
    }

    private double getMedianPrice(double[] prices) {
        int middle = prices.length / 2;
        return prices.length % 2 == 0 ?
                (prices[middle - 1] + prices[middle]) / 2 :
                prices[middle];
    }

    private void checkParams(List<Ticket> tickets, String origin, String destination) {
        checkIfListEmpty(tickets, "Список билетов пуст.");
        checkIfStringEmpty(origin, "Не указан код аэропорта вылета.");
        checkIfStringEmpty(destination, "Не указан код аэропорта прилета.");
    }

    private void checkIfListEmpty(List<?> list, String message) {
        if (list.isEmpty()) {
            throw new RuntimeException(message);
        }
    }

    private void checkIfStringEmpty(String param, String message) {
        if (param == null || param.isEmpty()) {
            throw new RuntimeException(message);
        }
    }
}
