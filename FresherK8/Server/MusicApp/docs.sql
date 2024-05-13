-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 13, 2024 lúc 12:41 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

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
  `user_id` varchar(100) NOT NULL,
  `song_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `album_love`
--

INSERT INTO `album_love` (`album_love_id`, `user_id`, `song_id`) VALUES
(12, '7oEPyI8hGqUB45vZNkMv3tsWahB2', 3),
(14, 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3', 1),
(13, 'tbWSmMgy1Uc4igHWLkA2WANhhUp2', 9);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `artist`
--

CREATE TABLE `artist` (
  `artist_id` int(11) NOT NULL,
  `artist_name` varchar(100) NOT NULL,
  `artist_image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `artist`
--

INSERT INTO `artist` (`artist_id`, `artist_name`, `artist_image`) VALUES
(1, 'Double2T', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2023/04/11/c/1/9/7/1681207645373.jpg'),
(2, 'Phan Mạnh Quỳnh', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2021/08/30/8/3/f/f/1630294420814.jpg'),
(3, 'Sơn Tùng M-TP', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2021/05/12/7/d/c/b/1620802736418.jpg'),
(4, 'Binz', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2019/09/12/c/3/c/7/1568253936065.jpg'),
(5, 'RPT MCK', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2023/03/03/a/5/a/8/1677826163685.jpg'),
(6, 'HIEUTHUHAI', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2022/08/12/3/a/7/f/1660300989335.jpg'),
(7, 'Low G', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2022/03/02/b/c/6/4/1646192952353.jpg'),
(8, 'Tez', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2022/10/05/7/0/6/2/1664940579241.jpg'),
(9, 'Tlinh', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2023/08/11/3/c/2/e/1691749030077.jpg'),
(10, 'Bích Phương', 'https://avatar-ex-swe.nixcdn.com/singer/avatar/2021/06/28/b/2/0/9/1624861449651.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` text NOT NULL,
  `category_image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `category_image`) VALUES
(1, 'NONSTOP', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2022/12/13/6/1/9/f/1670899811147_org.jpg'),
(2, 'Tình yêu', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2020/09/03/4/5/9/8/1599120798895_org.jpg'),
(3, 'Acoustic', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2020/08/13/f/d/9/1/1597291906852_org.jpg'),
(4, 'Chill Out', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2021/03/09/2/9/3/f/1615284927743_org.jpg'),
(5, 'Gym', 'https://avatar-ex-swe.nixcdn.com/topic/thumb/2022/10/31/7/f/c/2/1667207540646_org.jpg'),
(6, 'V-Rap', 'https://avatar-ex-swe.nixcdn.com/topic/mobile/2024/04/16/f/d/3/d/1713237576082_org.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `music_video`
--

CREATE TABLE `music_video` (
  `music_video_id` varchar(100) NOT NULL,
  `music_video_name` text NOT NULL,
  `music_video_image` text NOT NULL,
  `music_video_time` varchar(25) NOT NULL,
  `music_video_proposal_new` tinyint(1) NOT NULL DEFAULT 0,
  `artist_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `music_video`
--

INSERT INTO `music_video` (`music_video_id`, `music_video_name`, `music_video_image`, `music_video_time`, `music_video_proposal_new`, `artist_id`, `topic_id`) VALUES
('3gNuUcPg1fk', 'BINZ - HIT ME UP (ft. NOMOVODKA) | OFFICIAL MV', 'https://i.ytimg.com/vi/3gNuUcPg1fk/maxresdefault.jpg', '5:37', 1, 4, 22),
('zoEtcR5EW08', 'SƠN TÙNG M-TP | CHÚNG TA CỦA TƯƠNG LAI | OFFICIAL MUSIC VIDEO', 'https://i.ytimg.com/vi/zoEtcR5EW08/maxresdefault.jpg', '4:37', 1, 3, 26),
('i0nd3NPJ4MI', 'HIEUTHUHAI - Không Thể Say (prod. by Kewtiie) l Official Video', 'https://i.ytimg.com/vi/i0nd3NPJ4MI/sddefault.jpg?v=643f6a59', '4:21', 1, 6, 26),
('sU8G7Q4rUA4', 'BÍCH PHƯƠNG - Nâng Chén Tiêu Sầu (Official M/V)', 'https://i.ytimg.com/vi/sU8G7Q4rUA4/sddefault.jpg?v=65e993e7', '4:11', 0, 10, 13),
('b_pcHCfW4S4', 'Double2T - Em Yêu Cô Ấy (Prod. Hải Ma) | 10 Năm Trước Album - OFFICIAL MUSIC VIDEO', 'https://i.ytimg.com/vi/b_pcHCfW4S4/sddefault.jpg?v=661cf9f6', '3:39', 1, 1, 29),
('-k5ylVYTRvU', 'Ở sâu trong tim này - Tez x Emily | Official Music Video', 'https://i.ytimg.com/vi/-k5ylVYTRvU/maxresdefault.jpg', '3:11', 0, 8, 29),
('fyMgBQioTLo', 'tlinh - nếu lúc đó (ft. 2pillz) | OFFICIAL MUSIC VIDEO', 'https://i.ytimg.com/vi/fyMgBQioTLo/maxresdefault.jpg', '5:25', 0, 9, 23),
('rwIOCoDtHyI', 'VỤ NỔ LỚN - KHÔNG QUAN TRỌNG', 'https://i.ytimg.com/vi/rwIOCoDtHyI/maxresdefault.jpg', '5:53', 1, 5, 28),
('JAZZn6Bue0c', 'Quá Sớm | Low G | Rap Nhà Làm', 'https://i.ytimg.com/vi/JAZZn6Bue0c/sddefault.jpg', '4:07', 0, 7, 29),
('h_1t3-6oWz4', 'SAU LỜI TỪ KHƯỚC - PHAN MẠNH QUỲNH | OFFICIAL MV (OST phim MAI, Đạo Diễn Trấn Thành)', 'https://i.ytimg.com/vi/mzqvF_rIOx8/maxresdefault.jpg', '4:23', 0, 2, 23);

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
(1, 'Gen Gì Gen Z', 'https://avatar-ex-swe.nixcdn.com/playlist/2024/04/08/3/4/7/a/1712581779195_300.jpg', 1),
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
  `user_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `playlist_user`
--

INSERT INTO `playlist_user` (`playlist_user_id`, `playlist_user_name`, `user_id`) VALUES
(1, 'Chil chil', 'ZCt6n9uDFjZ9lOOtPataEGxngfr2'),
(2, 'Rap', 'ZCt6n9uDFjZ9lOOtPataEGxngfr2'),
(3, 'Gym', 'ZCt6n9uDFjZ9lOOtPataEGxngfr2');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `playlist_user_love`
--

CREATE TABLE `playlist_user_love` (
  `playlist_user_song_love_id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `playlist_id` int(11) NOT NULL
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

--
-- Đang đổ dữ liệu cho bảng `playlist_user_song`
--

INSERT INTO `playlist_user_song` (`playlist_user_song_id`, `playlist_user_id`, `song_id`) VALUES
(1, 1, 2),
(2, 1, 5),
(4, 2, 4),
(3, 2, 7);

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
  `song_listen` int(11) NOT NULL,
  `download` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `song`
--

INSERT INTO `song` (`song_id`, `song_name`, `song_image`, `song_url`, `album_id`, `topic_id`, `playlist_id`, `song_listen`, `download`) VALUES
(1, 'Chạy Ngay Đi', 'https://upload.wikimedia.org/wikipedia/vi/thumb/8/85/Chay_ngay_di.png/220px-Chay_ngay_di.png', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/ChayNgayDi-SonTungMTP-5468704.mp3', 9, 27, 1, 100, 1),
(2, 'Buồn Hay Vui (Feat. Rpt Mck, Obito & Ronboogz) ', 'https://photo-resize-zmp3.zadn.vn/w600_r1x1_jpeg/cover/6/d/a/a/6daaea74ed8423b2a0e769011d6d3eb3.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/BuonHayVuiFeatRptMckObitoRonboogz-VSOULRPTMCKObitoRonboogz-13159599.mp3', 11, 29, 1, 80, 1),
(3, 'Bảo Tàng', 'https://i1.sndcdn.com/artworks-GjgUcervqHm9-0-t500x500.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/BaoTang-LowG-12625032.mp3', 13, 29, 1, 57, 1),
(4, 'KPI', 'https://photo-resize-zmp3.zadn.vn/w360_r1x1_jpeg/avatars/c/2/a/1/c2a154106353063625c9412ed20443a0.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/KPI-HIEUTHUHAI-11966370.mp3', 12, 18, 4, 34, 0),
(5, 'Simp Gái 808', 'https://i.ytimg.com/vi/d2Jb2LgAsYw/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDLzfDXYR1uPb8N60QDnLvKs1YpbA', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/SimpGai808-LowG-11976517.mp3', 13, 29, 5, 35, 0),
(6, 'Thư Gửi Mẹ', 'https://i.ytimg.com/vi/KZYAW1plg_M/maxresdefault.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/ThuGuiMe-Tez-13565635.mp3', 14, 30, 5, 54, 0),
(7, 'Đừng Làm Nó Phức Tạp', 'https://bazaarvietnam.vn/wp-content/uploads/2024/01/Harpers-Bazaar-tlinh-mo-bat-2024-voi-MV-dung-lam-no-phuc-tap_03.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/DungLamNoPhucTap-tlinh-13521882.mp3', 15, 29, 5, 54, 0),
(8, 'Chị Ngã Em Nâng', 'https://i1.sndcdn.com/artworks-000441954789-d6haiq-t500x500.jpg', 'https://archive.org/download/muon-anh-dau-winno-hustlang-robber-13672897_202404/ChiNgaEmNang-BichPhuong-5758252.mp3', 16, 11, 3, 43, 0),
(9, 'Tình Yêu Giữa Mùa Đông', 'https://photo-resize-zmp3.zmdcdn.me/w256_r1x1_jpeg/avatars/3/e/3e602dd0ab83a3a8c3f32309bb9a88f9_1460456703.jpg', 'https://archive.org/download/tinh-yeu-giua-mua-dong-phan-manh-quynh-3700927_20240423/TinhYeuGiuaMuaDong-PhanManhQuynh-3700927.mp3', 8, 13, 4, 100, 0),
(10, 'Vợ Người Ta', 'https://avatar-ex-swe.nixcdn.com/song/2018/05/10/6/5/d/f/1525966962511_640.jpg', 'https://archive.org/download/tinh-yeu-giua-mua-dong-phan-manh-quynh-3700927_20240423/VoNguoiTa-PhanManhQuynh-4109355.mp3', 8, 22, 2, 30, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `song_again`
--

CREATE TABLE `song_again` (
  `song_again_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `song_again`
--

INSERT INTO `song_again` (`song_again_id`, `song_id`, `user_id`) VALUES
(10, 2, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `song_love`
--

CREATE TABLE `song_love` (
  `song_love_id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `song_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `song_love`
--

INSERT INTO `song_love` (`song_love_id`, `user_id`, `song_id`) VALUES
(51, '3i0hJyaL2kNBKjLrT8t8uPhxsa32', 2),
(11, '7oEPyI8hGqUB45vZNkMv3tsWahB2', 2),
(48, 'Kct8HgBtVsaLhXE5J8FBfMPEFWr2', 1),
(53, 'QSZqSss49HOyRaPzQymitz8P2942', 1),
(54, 'QSZqSss49HOyRaPzQymitz8P2942', 2),
(56, 'QSZqSss49HOyRaPzQymitz8P2942', 4),
(45, 'XMe0wL5W9wMYZEpdae30GwG15JD3', 1),
(46, 'XMe0wL5W9wMYZEpdae30GwG15JD3', 2),
(47, 'XMe0wL5W9wMYZEpdae30GwG15JD3', 3),
(13, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 3),
(20, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 4),
(14, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 5),
(15, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 6),
(16, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 7),
(21, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 8),
(35, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 10);

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
(34, 3, 3),
(16, 3, 5),
(22, 4, 1),
(21, 4, 2),
(33, 4, 3),
(6, 4, 5),
(8, 5, 1),
(9, 5, 2),
(23, 5, 4),
(10, 6, 2),
(32, 6, 4),
(25, 6, 5),
(27, 7, 1),
(26, 7, 2),
(12, 7, 3),
(11, 7, 5),
(5, 8, 4),
(20, 8, 5),
(30, 9, 4),
(28, 9, 5),
(29, 10, 1),
(31, 10, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `topic`
--

CREATE TABLE `topic` (
  `topic_id` int(11) NOT NULL,
  `topic_name` text NOT NULL,
  `topic_image` text NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `topic`
--

INSERT INTO `topic` (`topic_id`, `topic_name`, `topic_image`, `category_id`) VALUES
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
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `user_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`user_id`) VALUES
('3i0hJyaL2kNBKjLrT8t8uPhxsa32'),
('7oEPyI8hGqUB45vZNkMv3tsWahB2'),
('aeBg8o0lO7aEeGsqyWqvaMhZvu13'),
('boCsxQUcmfNsvWQ7CU8SSsbSmkA3'),
('Fcdj4N26i8PpzzpQCFFSqw5BHLx2'),
('GxUfF3C40NeUQsrJhdJeTD4xWxx1'),
('HU0KmfuPLUXvFKgmghtkRoiyNif1'),
('Jy5byXeAJ2aEbTCuF0o4EOPId3q2'),
('Kct8HgBtVsaLhXE5J8FBfMPEFWr2'),
('LrBLAMC8kQfNhBP2dq0PTnrAOEz2'),
('mFjqHF12ifQh8t0bOVL0NkeyMIf1'),
('QRig9dL8kaYE02jFwnpAeKAFXNd2'),
('QSZqSss49HOyRaPzQymitz8P2942'),
('tbWSmMgy1Uc4igHWLkA2WANhhUp2'),
('XMe0wL5W9wMYZEpdae30GwG15JD3'),
('ZCt6n9uDFjZ9lOOtPataEGxngfr2');

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
-- Chỉ mục cho bảng `artist`
--
ALTER TABLE `artist`
  ADD PRIMARY KEY (`artist_id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Chỉ mục cho bảng `music_video`
--
ALTER TABLE `music_video`
  ADD KEY `artist_id` (`artist_id`,`topic_id`),
  ADD KEY `topic_id` (`topic_id`);

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
-- Chỉ mục cho bảng `playlist_user_love`
--
ALTER TABLE `playlist_user_love`
  ADD PRIMARY KEY (`playlist_user_song_love_id`),
  ADD KEY `playlistUser_id` (`user_id`,`playlist_id`),
  ADD KEY `song_id` (`playlist_id`);

--
-- Chỉ mục cho bảng `playlist_user_song`
--
ALTER TABLE `playlist_user_song`
  ADD PRIMARY KEY (`playlist_user_song_id`),
  ADD KEY `playlist_user_id` (`playlist_user_id`,`song_id`),
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
-- Chỉ mục cho bảng `song_love`
--
ALTER TABLE `song_love`
  ADD PRIMARY KEY (`song_love_id`),
  ADD KEY `user_id` (`user_id`,`song_id`),
  ADD KEY `song_id` (`song_id`);

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
  ADD PRIMARY KEY (`topic_id`),
  ADD KEY `category_id` (`category_id`);

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
  MODIFY `album_love_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `artist`
--
ALTER TABLE `artist`
  MODIFY `artist_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `playlist`
--
ALTER TABLE `playlist`
  MODIFY `playlist_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `playlist_user`
--
ALTER TABLE `playlist_user`
  MODIFY `playlist_user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `playlist_user_love`
--
ALTER TABLE `playlist_user_love`
  MODIFY `playlist_user_song_love_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `playlist_user_song`
--
ALTER TABLE `playlist_user_song`
  MODIFY `playlist_user_song_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `rank`
--
ALTER TABLE `rank`
  MODIFY `rank_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `song`
--
ALTER TABLE `song`
  MODIFY `song_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `song_again`
--
ALTER TABLE `song_again`
  MODIFY `song_again_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `song_love`
--
ALTER TABLE `song_love`
  MODIFY `song_love_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT cho bảng `song_rank`
--
ALTER TABLE `song_rank`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT cho bảng `topic`
--
ALTER TABLE `topic`
  MODIFY `topic_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

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
-- Các ràng buộc cho bảng `music_video`
--
ALTER TABLE `music_video`
  ADD CONSTRAINT `music_video_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `music_video_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `playlist_user`
--
ALTER TABLE `playlist_user`
  ADD CONSTRAINT `playlist_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `playlist_user_love`
--
ALTER TABLE `playlist_user_love`
  ADD CONSTRAINT `playlist_user_love_ibfk_1` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`playlist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `playlist_user_love_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `playlist_user_song`
--
ALTER TABLE `playlist_user_song`
  ADD CONSTRAINT `playlist_user_song_ibfk_1` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `playlist_user_song_ibfk_2` FOREIGN KEY (`playlist_user_id`) REFERENCES `playlist_user` (`playlist_user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `song`
--
ALTER TABLE `song`
  ADD CONSTRAINT `song_ibfk_2` FOREIGN KEY (`album_id`) REFERENCES `album` (`album_id`),
  ADD CONSTRAINT `song_ibfk_4` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `song_ibfk_5` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`playlist_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `song_again`
--
ALTER TABLE `song_again`
  ADD CONSTRAINT `song_again_ibfk_1` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`),
  ADD CONSTRAINT `song_again_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `song_love`
--
ALTER TABLE `song_love`
  ADD CONSTRAINT `song_love_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `song_love_ibfk_2` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `song_rank`
--
ALTER TABLE `song_rank`
  ADD CONSTRAINT `song_rank_ibfk_1` FOREIGN KEY (`song_rank`) REFERENCES `rank` (`rank_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `song_rank_ibfk_2` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `topic`
--
ALTER TABLE `topic`
  ADD CONSTRAINT `topic_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
