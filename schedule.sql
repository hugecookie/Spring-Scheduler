-- 데이터베이스 생성 (없으면 생성)
CREATE DATABASE IF NOT EXISTS schedule_db;
USE schedule_db;

-- 일정 테이블 (기본 일정 정보 저장)
CREATE TABLE IF NOT EXISTS schedules (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    password VARCHAR(255) NOT NULL,
    status ENUM('scheduled', 'ongoing', 'completed', 'canceled') NOT NULL DEFAULT 'scheduled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_updated_at TIMESTAMP NULL
    );

-- 일정 수정 이력 테이블 (수정할 때마다 기록)
CREATE TABLE IF NOT EXISTS schedule_history (
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                schedule_id INT NOT NULL,
                                                previous_title VARCHAR(255) NOT NULL,
    previous_description TEXT NULL,
    previous_date DATE NOT NULL,
    previous_time TIME NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
    );

-- 일정 참여자 테이블 (일정에 여러 명이 참여 가능)
CREATE TABLE IF NOT EXISTS schedule_participants (
                                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                                     schedule_id INT NOT NULL,
                                                     user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
    );

-- 일정 알림 테이블 (일정마다 여러 개의 알림 설정 가능)
CREATE TABLE IF NOT EXISTS schedule_notifications (
                                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                                      schedule_id INT NOT NULL,
                                                      notify_time DATETIME NOT NULL,
                                                      FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
    );
