package com.example.roomeaze;

public class BookingData {
    public String hostelName;
    public String roomNumber;
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public String problemDescription;

    public BookingData() {
    }


    public BookingData(String hostelName, String roomNumber, int year, int month, int day, int hour, int minute, String problemDescription) {
        this.hostelName = hostelName;
        this.roomNumber = roomNumber;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.problemDescription = problemDescription;
    }

    // Getters and setters for all fields
    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }
}
