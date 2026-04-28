-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 28, 2026 lúc 06:44 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `event_mng`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `carts`
--

CREATE TABLE `carts` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `status` enum('ACTIVE') DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart_items`
--

CREATE TABLE `cart_items` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL,
  `unit_price` decimal(12,2) NOT NULL,
  `cart_id` bigint(20) DEFAULT NULL,
  `ticket_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `events`
--

CREATE TABLE `events` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `province` varchar(255) DEFAULT NULL,
  `sale_end_date` datetime(6) DEFAULT NULL,
  `sale_start_date` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `status` enum('CANCELLED','CLOSED','COMPLETED','OPENING','PENDING','UPCOMING') DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `organizer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `event_images`
--

CREATE TABLE `event_images` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) NOT NULL,
  `event_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `invalidated_token`
--

CREATE TABLE `invalidated_token` (
  `id` varchar(255) NOT NULL,
  `expiry_time` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `discount_amount` decimal(12,2) DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `order_status` enum('CANCELLED','CONFIRMED','PENDING') DEFAULT NULL,
  `organizer_amount` decimal(12,2) NOT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `payment_method` enum('BANKING','MOMO','VNPAY') DEFAULT NULL,
  `payment_status` enum('FAILED','PAID','PENDING','REFUNDED') DEFAULT NULL,
  `platform_fee_rate` float NOT NULL,
  `service_fee` decimal(12,2) NOT NULL,
  `total_amount` decimal(12,2) NOT NULL,
  `voucher_code` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL,
  `unit_price` decimal(12,2) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `ticket_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `permissions`
--

CREATE TABLE `permissions` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`name`, `description`) VALUES
('ADMIN', 'Administrator');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_permissions`
--

CREATE TABLE `role_permissions` (
  `role_name` varchar(255) NOT NULL,
  `permission_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tickets`
--

CREATE TABLE `tickets` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `qr_code` varchar(255) DEFAULT NULL,
  `status` enum('CANCELLED','REFUNDED','USED','VALID') DEFAULT NULL,
  `ticket_code` varchar(255) NOT NULL,
  `used_at` datetime(6) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `ticket_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ticket_types`
--

CREATE TABLE `ticket_types` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `remaining_quantity` int(11) NOT NULL,
  `total_quantity` int(11) NOT NULL,
  `event_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `otp_expiry` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `verification_token` varchar(255) DEFAULT NULL,
  `organizer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `created_at`, `updated_at`, `address`, `email`, `enabled`, `full_name`, `otp`, `otp_expiry`, `password`, `phone`, `username`, `verification_token`, `organizer_id`) VALUES
(1, '2026-04-28 23:26:43.000000', '2026-04-28 23:26:43.000000', NULL, 'admin@gmail.com', b'1', 'ADMIN-MANAGERMENT', NULL, NULL, '$2a$10$y/C7it7vj0xEX7g6FlX/XeQsXPqpwYWrihKg4mRt4Svp/0hBEOsBa', '0123456789', 'admin', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 'ADMIN');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vouchers`
--

CREATE TABLE `vouchers` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `amount` decimal(38,2) NOT NULL,
  `code` varchar(255) NOT NULL,
  `discount_type` varchar(255) NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `max_discount` decimal(38,2) DEFAULT NULL,
  `min_order_amount` decimal(38,2) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK88sv4i13lo80s74ox7rsb5a2c` (`customer_id`);

--
-- Chỉ mục cho bảng `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpcttvuq4mxppo8sxggjtn5i2c` (`cart_id`),
  ADD KEY `FKapt896r3qpq7yl34hqjixxlix` (`ticket_type_id`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo6mla8j1p5bokt4dxrlmgwc28` (`category_id`),
  ADD KEY `FKdocju8m76a3f8o6ljh2jrn2ra` (`organizer_id`);

--
-- Chỉ mục cho bảng `event_images`
--
ALTER TABLE `event_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK90aphyy4imr1x6rvklnvtmat6` (`event_id`);

--
-- Chỉ mục cho bảng `invalidated_token`
--
ALTER TABLE `invalidated_token`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsjfs85qf6vmcurlx43cnc16gy` (`customer_id`);

--
-- Chỉ mục cho bảng `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  ADD KEY `FKpcp79oqd3cihkrcb4j0bojtor` (`ticket_type_id`);

--
-- Chỉ mục cho bảng `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`name`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`name`);

--
-- Chỉ mục cho bảng `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD PRIMARY KEY (`role_name`,`permission_name`),
  ADD KEY `FKie537gni0kx7t0vxakg9kbur9` (`permission_name`);

--
-- Chỉ mục cho bảng `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKcvl4jbu5fln08ltem9rrmtp8w` (`ticket_code`),
  ADD KEY `FKqgi3sbv1u45s41wawh75ut8ph` (`order_id`),
  ADD KEY `FKotik7mbbb14hu8n9og7o92k5h` (`ticket_type_id`);

--
-- Chỉ mục cho bảng `ticket_types`
--
ALTER TABLE `ticket_types`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl83j9knh8jrssp3skaeubrrk` (`event_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  ADD KEY `FKjhfycgkevqpdnf6mitvouimjc` (`organizer_id`);

--
-- Chỉ mục cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- Chỉ mục cho bảng `vouchers`
--
ALTER TABLE `vouchers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK30ftp2biebbvpik8e49wlmady` (`code`),
  ADD KEY `FK370ue1hndkysixfce068nhp5w` (`created_by`),
  ADD KEY `FKdc5lk17jgy5avc1gpsxla0suv` (`event_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `carts`
--
ALTER TABLE `carts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `events`
--
ALTER TABLE `events`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `event_images`
--
ALTER TABLE `event_images`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tickets`
--
ALTER TABLE `tickets`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `ticket_types`
--
ALTER TABLE `ticket_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `vouchers`
--
ALTER TABLE `vouchers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `FK99i1rh5nm7r3f1b3wdcuq5h57` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `FKapt896r3qpq7yl34hqjixxlix` FOREIGN KEY (`ticket_type_id`) REFERENCES `ticket_types` (`id`),
  ADD CONSTRAINT `FKpcttvuq4mxppo8sxggjtn5i2c` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`);

--
-- Các ràng buộc cho bảng `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `FKdocju8m76a3f8o6ljh2jrn2ra` FOREIGN KEY (`organizer_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKo6mla8j1p5bokt4dxrlmgwc28` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Các ràng buộc cho bảng `event_images`
--
ALTER TABLE `event_images`
  ADD CONSTRAINT `FK90aphyy4imr1x6rvklnvtmat6` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FKsjfs85qf6vmcurlx43cnc16gy` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `FKpcp79oqd3cihkrcb4j0bojtor` FOREIGN KEY (`ticket_type_id`) REFERENCES `ticket_types` (`id`);

--
-- Các ràng buộc cho bảng `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD CONSTRAINT `FKie537gni0kx7t0vxakg9kbur9` FOREIGN KEY (`permission_name`) REFERENCES `permissions` (`name`),
  ADD CONSTRAINT `FKq893o9jg7ihgdxfwtiswn0uk2` FOREIGN KEY (`role_name`) REFERENCES `roles` (`name`);

--
-- Các ràng buộc cho bảng `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FKotik7mbbb14hu8n9og7o92k5h` FOREIGN KEY (`ticket_type_id`) REFERENCES `ticket_types` (`id`),
  ADD CONSTRAINT `FKqgi3sbv1u45s41wawh75ut8ph` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Các ràng buộc cho bảng `ticket_types`
--
ALTER TABLE `ticket_types`
  ADD CONSTRAINT `FKl83j9knh8jrssp3skaeubrrk` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKjhfycgkevqpdnf6mitvouimjc` FOREIGN KEY (`organizer_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`name`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `vouchers`
--
ALTER TABLE `vouchers`
  ADD CONSTRAINT `FK370ue1hndkysixfce068nhp5w` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKdc5lk17jgy5avc1gpsxla0suv` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
