-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 21, 2024 lúc 04:56 PM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `music`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `album`
--

CREATE TABLE `album` (
  `album_id` int(11) NOT NULL,
  `album_name` text NOT NULL,
  `album_image` text NOT NULL,
  `album_new` tinyint(1) NOT NULL,
  `name_artist` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `album`
--

INSERT INTO `album` (`album_id`, `album_name`, `album_image`, `album_new`, `name_artist`) VALUES
(7, 'Top Songs: Double2T - Double2T', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/10/29/T/3/N/M/1698564351580.jpg', 1, 'Double2T'),
(8, 'Tình nhớ (Single)', 'https://avatar-ex-swe.nixcdn.com/playlist/2022/06/24/6/6/b/2/1656067279527.jpg', 1, 'Phan Mạnh Quỳnh'),
(9, 'm-tp M-TP', 'https://avatar-ex-swe.nixcdn.com/playlist/2019/06/27/4/7/d/4/1561624732405.jpg', 1, 'Sơn Tùng M-TP'),
(10, 'Đan Xinh In Love', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/10/30/c/f/0/e/1698673576442.jpg', 1, 'Binz'),
(11, 'Phong Cách(Single)', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/11/09/8/9/1/7/1699494787483.jpg', 1, 'RPT MCK'),
(12, 'HIEUTHUHAI', 'https://photo-resize-zmp3.zadn.vn/w600_r1x1_jpeg/cover/d/8/8/9/d889c3716051c43db853e261a42112a7.jpg', 0, 'Hiếu Thứ Hai'),
(13, 'Top Songs: Low G', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/03/09/9/2/5/9/1678345136464.jpg', 0, 'Low G'),
(14, 'M.E.R(Mê Em Rồi)(Single)', 'https://yt3.googleusercontent.com/gubhUPZ77i4__nmgOPRgNhaebJmPUe-9xujYk6hsqz-lDwo75zQLSGuQ5xyXHa4YnKx1-cUU=s900-c-k-c0x00ffffff-no-rj', 0, 'Tez'),
(15, 'Sucker For Love', 'https://avatar-ex-swe.nixcdn.com/playlist/2021/06/07/8/c/6/8/1623036837842.jpg', 1, 'Tlinh'),
(16, 'Chỉ Ngã Em Nâng', 'https://i1.sndcdn.com/artworks-000441954789-d6haiq-t500x500.jpg', 1, 'Bích Phương');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `album_love`
--

CREATE TABLE `album_love` (
  `album_love_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `album_love`
--

INSERT INTO `album_love` (`album_love_id`, `user_id`, `song_id`) VALUES
(3, 1, 1),
(1, 1, 4),
(11, 1, 6),
(10, 2, 4),
(2, 2, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` text NOT NULL,
  `category_image` text NOT NULL,
  `topic_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `category_image`, `topic_id`) VALUES
(11, 'Love Songs And Chill', 'https://avatar-ex-swe.nixcdn.com/playlist/2024/01/26/c/5/d/c/1706243617581.jpg', 2),
(12, 'Valentine Rap Love', 'https://avatar-ex-swe.nixcdn.com/playlist/2024/03/07/7/b/a/6/1709802931692.jpg', 2),
(13, 'Mùa yêu', 'https://avatar-ex-swe.nixcdn.com/playlist/2024/02/05/6/6/8/d/1707128039037.jpg', 2),
(14, 'Nonstop Em Ơi Lên Sàn', 'https://avatar-ex-swe.nixcdn.com/playlist/2020/05/23/7/c/0/d/1590214538495.jpg', 1),
(15, 'Siêu Phẩm Nonstop Căng Đét', 'https://avatar-ex-swe.nixcdn.com/playlist/2020/05/05/3/b/8/e/1588665043304.jpg', 1),
(18, 'Rap Workout', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/04/03/c/0/2/5/1680516455596.jpg', 5),
(19, 'Gym Motivation', 'https://avatar-ex-swe.nixcdn.com/playlist/2022/12/20/2/7/7/b/1671473894826.jpg', 5),
(22, 'Acoustic Chill', 'https://avatar-ex-swe.nixcdn.com/playlist/2022/12/14/2/d/d/9/1671013466214.jpg', 3),
(23, 'Acoustic Horizons', 'https://avatar-ex-swe.nixcdn.com/playlist/2022/05/31/3/4/d/2/1653944040370.jpg', 3),
(26, 'Pop Chill', 'https://avatar-ex-swe.nixcdn.com/playlist/2022/12/15/f/7/7/6/1671096330661.jpg', 4),
(27, 'Lofi & Chill', 'https://avatar-ex-swe.nixcdn.com/playlist/2022/12/22/6/1/4/b/1671683733401.jpg', 4),
(28, 'Rap Việt Ngầu', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/06/12/7/e/d/e/1686568234720.jpg', 6),
(29, 'Rap Việt Hay Nhất', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/02/09/1/b/a/0/1675938905541.jpg', 6),
(30, 'Rap Việt Mùa 3', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/07/31/5/0/0/8/1690797270629.jpg', 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `playlist`
--

CREATE TABLE `playlist` (
  `playlist_id` int(11) NOT NULL,
  `playlist_name` text NOT NULL,
  `playlist_image` text NOT NULL,
  `mood_today` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `playlist`
--

INSERT INTO `playlist` (`playlist_id`, `playlist_name`, `playlist_image`, `mood_today`) VALUES
(1, 'Gen Gì Gen Z', 'https://avatar-ex-swe.nixcdn.com/playlist/2024/04/08/3/4/7/a/1712581779195_300.jpg', 0),
(2, 'Nhạc Việt Chill', 'https://avatar-ex-swe.nixcdn.com/playlist/2024/04/02/0/c/e/b/1712025492299_300.jpg', 1),
(3, 'Spep Up Việt', 'https://avatar-ex-swe.nixcdn.com/playlist/2023/07/18/a/8/3/9/1689666142360_300.jpg', 1),
(4, 'La Cà Cà Phê', 'https://avatar-ex-swe.nixcdn.com/playlist/2024/04/02/0/c/e/b/1712029980926_300.jpg', 1),
(5, 'Nhạc V-Rap Hot', 'https://avatar-ex-swe.nixcdn.com/playlist/2022/04/29/b/a/8/6/1651225034969.jpg', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `playlist_user`
--

CREATE TABLE `playlist_user` (
  `playlist_user_id` int(11) NOT NULL,
  `playlist_user_name` text NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `playlist_user_song`
--

CREATE TABLE `playlist_user_song` (
  `playlist_user_song_id` int(11) NOT NULL,
  `playlist_user_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rank`
--

CREATE TABLE `rank` (
  `rank_id` int(11) NOT NULL,
  `rank_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `rank`
--

INSERT INTO `rank` (`rank_id`, `rank_name`) VALUES
(1, 'Trending Music'),
(2, 'Rap Việt'),
(3, 'Nonstop'),
(4, 'V-POP'),
(5, 'Youtube Trending');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `song`
--

CREATE TABLE `song` (
  `song_id` int(11) NOT NULL,
  `song_name` text NOT NULL,
  `song_image` text NOT NULL,
  `song_url` text NOT NULL,
  `album_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `playlist_id` int(11) NOT NULL,
  `song_listen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `song`
--

INSERT INTO `song` (`song_id`, `song_name`, `song_image`, `song_url`, `album_id`, `topic_id`, `playlist_id`, `song_listen`) VALUES
(1, 'Chạy Ngay Đi', 'https://upload.wikimedia.org/wikipedia/vi/thumb/8/85/Chay_ngay_di.png/220px-Chay_ngay_di.png', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/ChayNgayDi-SonTungMTP-5468704.mp3', 9, 27, 1, 100),
(2, 'Buồn Hay Vui (Feat. Rpt Mck, Obito & Ronboogz) ', 'https://photo-resize-zmp3.zadn.vn/w600_r1x1_jpeg/cover/6/d/a/a/6daaea74ed8423b2a0e769011d6d3eb3.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/BuonHayVuiFeatRptMckObitoRonboogz-VSOULRPTMCKObitoRonboogz-13159599.mp3', 11, 29, 1, 80),
(3, 'Bảo Tàng', 'https://i1.sndcdn.com/artworks-GjgUcervqHm9-0-t500x500.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/BaoTang-LowG-12625032.mp3', 13, 29, 1, 57),
(4, 'KPI', 'https://photo-resize-zmp3.zadn.vn/w360_r1x1_jpeg/avatars/c/2/a/1/c2a154106353063625c9412ed20443a0.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/KPI-HIEUTHUHAI-11966370.mp3', 12, 18, 4, 34),
(5, 'Simp Gái 808', 'https://i.ytimg.com/vi/d2Jb2LgAsYw/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDLzfDXYR1uPb8N60QDnLvKs1YpbA', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/SimpGai808-LowG-11976517.mp3', 13, 29, 5, 35),
(6, 'Thư Gửi Mẹ', 'https://i.ytimg.com/vi/KZYAW1plg_M/maxresdefault.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/ThuGuiMe-Tez-13565635.mp3', 14, 30, 5, 54),
(7, 'Đừng Làm Nó Phức Tạp', 'https://bazaarvietnam.vn/wp-content/uploads/2024/01/Harpers-Bazaar-tlinh-mo-bat-2024-voi-MV-dung-lam-no-phuc-tap_03.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/DungLamNoPhucTap-tlinh-13521882.mp3', 15, 29, 5, 54),
(8, 'Chị Ngã Em Nâng', 'https://i1.sndcdn.com/artworks-000441954789-d6haiq-t500x500.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/ChiNgaEmNang-BichPhuong-5758252.mp3', 16, 11, 3, 43);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `song_again`
--

CREATE TABLE `song_again` (
  `song_again_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `song_again`
--

INSERT INTO `song_again` (`song_again_id`, `song_id`, `user_id`) VALUES
(6, 2, 1),
(7, 3, 1),
(8, 1, 1),
(9, 8, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `song_rank`
--

CREATE TABLE `song_rank` (
  `id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `song_rank` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `song_rank`
--

INSERT INTO `song_rank` (`id`, `song_id`, `song_rank`) VALUES
(4, 1, 1),
(17, 1, 3),
(7, 1, 5),
(2, 2, 1),
(1, 2, 2),
(13, 2, 3),
(3, 3, 2),
(15, 3, 2),
(16, 3, 5),
(22, 4, 1),
(21, 4, 2),
(6, 4, 5),
(8, 5, 1),
(9, 5, 2),
(23, 5, 4),
(10, 6, 2),
(25, 6, 5),
(27, 7, 1),
(26, 7, 2),
(12, 7, 3),
(11, 7, 5),
(5, 8, 4),
(20, 8, 5);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `topic`
--

CREATE TABLE `topic` (
  `topic_id` int(11) NOT NULL,
  `topic_name` text NOT NULL,
  `topic_image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `topic`
--

INSERT INTO `topic` (`topic_id`, `topic_name`, `topic_image`) VALUES
(1, 'NONSTOP', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2022/12/13/6/1/9/f/1670899811147_org.jpg'),
(2, 'Tình yêu', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2020/09/03/4/5/9/8/1599120798895_org.jpg'),
(3, 'Acoustic', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2020/08/13/f/d/9/1/1597291906852_org.jpg'),
(4, 'Chill Out', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2021/03/09/2/9/3/f/1615284927743_org.jpg'),
(5, 'Gym', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2022/10/31/7/f/c/2/1667207540646_org.jpg'),
(6, 'V-Rap', 'https://avatar-ex-swe.nixcdn.com/topic/mobile/2024/04/16/f/d/3/d/1713237576082_org.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`user_id`) VALUES
(1),
(2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `album`
--
ALTER TABLE `album`
  ADD PRIMARY KEY (`album_id`);

--
-- Chỉ mục cho bảng `album_love`
--
ALTER TABLE `album_love`
  ADD PRIMARY KEY (`album_love_id`),
  ADD KEY `user_id` (`user_id`,`song_id`),
  ADD KEY `song_id` (`song_id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `category_id` (`topic_id`);

--
-- Chỉ mục cho bảng `playlist`
--
ALTER TABLE `playlist`
  ADD PRIMARY KEY (`playlist_id`);

--
-- Chỉ mục cho bảng `playlist_user`
--
ALTER TABLE `playlist_user`
  ADD PRIMARY KEY (`playlist_user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `playlist_user_song`
--
ALTER TABLE `playlist_user_song`
  ADD PRIMARY KEY (`playlist_user_song_id`),
  ADD KEY `playlistUser_id` (`playlist_user_id`,`song_id`),
  ADD KEY `song_id` (`song_id`);

--
-- Chỉ mục cho bảng `rank`
--
ALTER TABLE `rank`
  ADD PRIMARY KEY (`rank_id`);

--
-- Chỉ mục cho bảng `song`
--
ALTER TABLE `song`
  ADD PRIMARY KEY (`song_id`),
  ADD KEY `artist_id` (`album_id`),
  ADD KEY `topic_id` (`topic_id`),
  ADD KEY `playlist_id` (`playlist_id`);

--
-- Chỉ mục cho bảng `song_again`
--
ALTER TABLE `song_again`
  ADD PRIMARY KEY (`song_again_id`),
  ADD KEY `song_id` (`song_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `song_rank`
--
ALTER TABLE `song_rank`
  ADD PRIMARY KEY (`id`),
  ADD KEY `song_id` (`song_id`,`song_rank`),
  ADD KEY `song_rank` (`song_rank`);

--
-- Chỉ mục cho bảng `topic`
--
ALTER TABLE `topic`
  ADD PRIMARY KEY (`topic_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `album`
--
ALTER TABLE `album`
  MODIFY `album_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `album_love`
--
ALTER TABLE `album_love`
  MODIFY `album_love_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT cho bảng `playlist`
--
ALTER TABLE `playlist`
  MODIFY `playlist_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `playlist_user`
--
ALTER TABLE `playlist_user`
  MODIFY `playlist_user_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `playlist_user_song`
--
ALTER TABLE `playlist_user_song`
  MODIFY `playlist_user_song_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `rank`
--
ALTER TABLE `rank`
  MODIFY `rank_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `song`
--
ALTER TABLE `song`
  MODIFY `song_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `song_again`
--
ALTER TABLE `song_again`
  MODIFY `song_again_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `song_rank`
--
ALTER TABLE `song_rank`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT cho bảng `topic`
--
ALTER TABLE `topic`
  MODIFY `topic_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `album_love`
--
ALTER TABLE `album_love`
  ADD CONSTRAINT `album_love_ibfk_2` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `album_love_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `category_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `playlist_user`
--
ALTER TABLE `playlist_user`
  ADD CONSTRAINT `playlist_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Các ràng buộc cho bảng `playlist_user_song`
--
ALTER TABLE `playlist_user_song`
  ADD CONSTRAINT `playlist_user_song_ibfk_1` FOREIGN KEY (`playlist_user_id`) REFERENCES `playlist_user` (`playlist_user_id`),
  ADD CONSTRAINT `playlist_user_song_ibfk_2` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `song`
--
ALTER TABLE `song`
  ADD CONSTRAINT `song_ibfk_2` FOREIGN KEY (`album_id`) REFERENCES `album` (`album_id`),
  ADD CONSTRAINT `song_ibfk_4` FOREIGN KEY (`topic_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `song_ibfk_5` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`playlist_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `song_again`
--
ALTER TABLE `song_again`
  ADD CONSTRAINT `song_again_ibfk_1` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`),
  ADD CONSTRAINT `song_again_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `song_rank`
--
ALTER TABLE `song_rank`
  ADD CONSTRAINT `song_rank_ibfk_1` FOREIGN KEY (`song_rank`) REFERENCES `rank` (`rank_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `song_rank_ibfk_2` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
