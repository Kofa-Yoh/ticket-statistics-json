package com.kotkina;

import com.kotkina.models.Ticket;
import com.kotkina.models.TicketList;
import com.kotkina.services.JsonFileReader;
import com.kotkina.services.TicketStatistics;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TicketStatisticsDemo {

    private static final String ORIGIN_CODE = "VVO";
    private static final String DESTINATION_CODE = "TLV";

    public static void main(String[] args) {
        System.out.println("Введите путь к файлу:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();

        TicketList content = null;
        try {
            content = new JsonFileReader().getContent(path, TicketList.class);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения json-файла: " + e.getMessage());
        }

        if (content == null) {
            throw new RuntimeException("Не удалось прочитать json-файл. Файл пуст.");
        }

        TicketStatistics statistics = new TicketStatistics();
        List<Ticket> tickets = content.getTickets();

        Map<String, Long> minFlightTime = statistics.getMinFlightTime(tickets, ORIGIN_CODE, DESTINATION_CODE);
        StringBuilder mapOutput = new StringBuilder();
        for (Map.Entry<String, Long> entry : minFlightTime.entrySet()) {
            mapOutput.append(String.format("%s - %s мин.%n", entry.getKey(), entry.getValue()));
        }
        System.out.println("Минимальное время полета между городами Владивосток и Тель-Авив для авиакомпаний:" + System.lineSeparator() +
                mapOutput);

        System.out.println("Разница между средней ценой и медианой для полета между городами  Владивосток и Тель-Авив: " + System.lineSeparator() +
                statistics.getAverageAndMedianPricesDifference(tickets, ORIGIN_CODE, DESTINATION_CODE));
    }
}