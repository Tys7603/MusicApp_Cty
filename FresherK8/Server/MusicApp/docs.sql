-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 04, 2024 lúc 11:41 AM
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
-- Cấu trúc bảng cho bảng `follow`
--

CREATE TABLE `follow` (
  `follow_id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `artist_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `follow`
--

INSERT INTO `follow` (`follow_id`, `user_id`, `artist_id`) VALUES
(27, 'NrZk7AA9LmfTQF1t3CbkQgeGYVh1', 3),
(26, 'NrZk7AA9LmfTQF1t3CbkQgeGYVh1', 8),
(24, 'QSZqSss49HOyRaPzQymitz8P2942', 3),
(20, 'QSZqSss49HOyRaPzQymitz8P2942', 4),
(11, 'QSZqSss49HOyRaPzQymitz8P2942', 5),
(25, 'QSZqSss49HOyRaPzQymitz8P2942', 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lyric`
--

CREATE TABLE `lyric` (
  `lyric_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `lyric_text` text NOT NULL,
  `startMs` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `lyric`
--

INSERT INTO `lyric` (`lyric_id`, `song_id`, `lyric_text`, `startMs`) VALUES
(1, 3, '(Maiki \'bout to flip, ey)', 5020),
(2, 3, 'Low G có c- đẹp và tao nghĩ nó nên được trưng bày trong lồng viện bảo tàng', 13760),
(3, 3, 'Ướp nó lạnh xong để vào bảo quản, tiền bảo đảm là cả một đảo vàng', 17470),
(4, 3, 'Lúc đấy thì tao chết xừ òi, nhưng con cháu được uống rượu hảo hạng', 20850),
(5, 3, 'Còn tiền thừa cho đi tán gái, party linh tinh để nó bảo bạn', 24360),
(6, 3, 'Cụ Low G flex, cụ Low G căng', 27400),
(7, 3, 'Low G check là cụ Low G chăn', 29570),
(8, 3, 'Low G đua xe ô tô Civic', 31130),
(9, 3, 'Low G nhà vô địch Olympic, về flex', 32910),
(10, 3, 'Chúng nó vẫn cứ lại bảo là tao khệnh', 35370),
(11, 3, 'Tao bảo là, \"Chuẩn òi!\"', 36950),
(12, 3, 'Khệnh tự tin, khệnh cá tính', 37920),
(13, 3, 'Xong lúc già tao sẽ kể cho con cháu là', 39500),
(14, 3, 'Tao là thằng kì cục nhưng được cái đẹp trai', 41650),
(15, 3, 'Có duyên, thân thiện, hài hước, cuốn hút', 43410),
(16, 3, 'Đỉnh cao, biết lắng nghe, ga lăng, nam tính, giàu', 45070),
(17, 3, 'Hết cả hơi', 48020),
(18, 3, 'Nói chung là hút gái', 49960),
(19, 3, 'Đi tán gái tao hâm hâm dở dở nhưng mà không hiểu sao các em nhắn tao hoài', 50880),
(20, 3, 'Đưa một em lên hotel Hàng Bài, em đang cởi áo tự nhiên tao dừng lại', 54290),
(21, 3, 'Tao bảo em, \"View Hồ Tây đẹp quá em ạ!\", xong ra cửa sổ rap freestyle', 57970),
(22, 3, 'Người Hà Nội nên là tính tao cục và đôi khi em ấy hứng lúc tao chửi bậy (ơ, thật à?)', 61300),
(23, 3, 'Hơi kì nhưng mà đúng đấy (mm)', 66930),
(24, 3, 'Tao thấy cứng và nó đứng lúc tao ngủ dậy', 68210),
(25, 3, 'Và nó dài đến mức có thể đu dây (ey)', 70110),
(26, 3, 'Nếu tao spam \"đm, vcl\" khi nói chuyện thì cô ấy coi như thần chú á', 71680),
(27, 3, 'Em ấy có hai thứ cong, một là mông, hai là giới tính (yeah)', 75200),
(28, 3, 'Vòng một là filler bên trong, vừa làm về, trông mới tinh (ah)', 78550),
(29, 3, 'Em ấy éo hiểu đang nghe rap, hay đang nghe Low G podcast (ey)', 81950),
(30, 3, 'Low G thư giãn, Low G nói về những chủ đề suy ngẫm như là', 85450),
(31, 3, 'Low G có c- đẹp và tao nghĩ nó nên được trưng bày trong lồng viện bảo tàng', 88350),
(32, 3, 'Ướp nó lạnh xong để vào bảo quản, tiền bảo đảm là cả một đảo vàng', 92080),
(33, 3, 'Lúc đấy thì tao chết xừ òi, nhưng con cháu được uống rượu hảo hạng', 95420),
(34, 3, 'Còn tiền thừa cho đi tán gái, party linh tinh để nó bảo bạn', 98920),
(35, 3, 'Cụ Low G flex, cụ Low G căng', 102330),
(36, 3, 'Low G check là cụ Low G chăn', 104830),
(37, 3, 'Low G đua xe ô tô Civic', 106470),
(38, 3, 'Low G nhà vô địch Olympic, về flex', 108410),
(39, 3, 'Chúng nó vẫn cứ lại bảo là tao khệnh', 111550),
(40, 3, 'Tao bảo là, \"Chuẩn òi!\"', 113240),
(41, 3, 'Khệnh tự tin, khệnh cá tính', 114160),
(42, 3, 'Xong lúc già tao sẽ kể cho con cháu là', 116030),
(43, 3, 'Tao là thằng kì cục nhưng được cái đẹp trai', 118240),
(44, 3, 'Có duyên, thân thiện, hài hước, cuốn hút', 119930),
(45, 3, 'Đỉnh cao, biết lắng nghe, ga lăng, nam tính, giàu', 121940),
(46, 3, 'Hết cả hơi', 125870),
(47, 3, 'Nói chung là hút gái', 129260),
(48, 3, 'Fan Low Gờ nên em H \"An Thần\" cả năm vừa rồi mỗi khi em đang high', 132950),
(49, 3, 'Một phút đầu tao thơm má em, xong năm phút sau tao chơi má em (cái gì?)', 137230),
(50, 3, 'Em muốn cosplay AMEE, nên tao vẽ ria mèo lên má em (à) (meo mèo meo meo)', 140050),
(51, 3, 'Ba em nhìn mặt tao thấy khá quen, hỏi tao là thằng nào', 143250),
(52, 3, 'Tao vừa nói tên tao, chú ấy cúi đầu xuống chào (sheesh)', 146800),
(53, 3, 'Em tao ở nhà penthouse rủ ghệ bem nhau đêm sau nhưng mà không ổn lắm', 150160),
(54, 3, 'Suốt ngày truyền năng lượng tích cực nhưng để giấu chuyện gì đấy trong một năm (ey)', 153670),
(55, 3, 'Turned out em làm booking bar, bảo sao cứ gặp ai là em ý quý', 157610),
(56, 3, 'Lan tỏa tình yêu khắp mọi lúc, xong em lan tỏa luôn con mẹ STDs', 160880),
(57, 3, 'Ơ, thế không biết STD là gì à?', 163750),
(58, 3, 'Thôi lên mạng search Google đi, đừng bắt người ta phải giải thích', 168210),
(59, 3, 'Không lại bảo là, \"Anh Long rap gì mà bậy thế, rap gì mà ghê thế, eo ôi!', 170630),
(60, 3, 'Rap gì mà STDs các kiểu\"', 125870),
(61, 3, 'Low G có c- đẹp và tao nghĩ nó nên được trưng bày trong lồng viện bảo tàng', 129260),
(62, 4, 'Hah', 4150),
(63, 4, 'Yo', 8180),
(64, 4, 'Mày biết là tại sao mà tao hay đặt KPI không?', 9750),
(65, 4, 'Bởi vì nếu mà không có KPI á', 13140),
(66, 4, 'Tao sợ là tao sẽ không bao giờ biết được điểm dừng', 15130),
(67, 4, 'Let\'s go!', 20980),
(68, 4, 'Bật cái beat lớn lên Kew ơi!', 24950),
(69, 4, 'KPI, KPI, ah', 31250),
(70, 4, 'KPI, KPI, ah', 33570),
(71, 4, 'KPI, KPI, ah', 35820),
(72, 4, '(Hey, Kewtiie)', 37210),
(73, 4, 'Năm sau có nhiều KPI', 38710),
(74, 4, 'Mua con xe hay là thêm cái nhà?', 40940),
(75, 4, 'Toàn hit, cho ra thêm mấy bài?', 43460),
(76, 4, 'Everybody, yeah, we go, go, go', 45720),
(77, 4, 'Tao siêng khi mày xay tí xoài, ha-ha', 48220),
(78, 4, 'Ey, one, two, three', 50430),
(79, 4, 'Như mọi năm lại làm new plan lúc mùa thu sang, nếu như mày then too bad', 52710),
(80, 4, 'Làm việc theo cảm xúc, oh, motherfucker, I don\'t do that (nah)', 56070),
(81, 4, 'Tiền vào new bank, số không tao đông như băng nhạc Wu-tang', 58320),
(82, 4, 'Họ nói tao giết con beat thì mày nên biết đó không phải vu oan (you know)', 60440),
(83, 4, 'Như bệnh nhân phẫu thuật, tao có rất nhiều chỉ tiêu', 62830),
(84, 4, 'Luôn đội lên đầu không phải để quăng giống như là Durag', 64880),
(85, 4, 'Hater nói ít thôi vì anh là Cantona (Cantona)', 67050),
(86, 4, 'Anh chỉ đấu với người giỏi nhất như anh là Barbosa (Barbosa)', 69100),
(87, 4, 'Từng trực đêm hết cả ca tối và kiếm được ba đô la (ba thôi)', 71350),
(88, 4, 'Giờ tài khoản như đang trên sàn và nhảy điệu cha-cha-cha', 73590),
(89, 4, 'Và mục tiêu là bán cổ phiếu rồi về báo hiếu cho mommy (oh)', 76080),
(90, 4, 'Tất cả show có dàn đồng ca và fan hét to như Ponzi', 78380),
(91, 4, 'Xe hai cửa chạy V12 như nó là ba của Henry', 80580),
(92, 4, 'KPI trải đầy ra bàn so you know I\'m hungry', 83000),
(93, 4, 'First class bay trên Boeing (Boeing)', 85340),
(94, 4, 'Chất xám trên con Rollie (Rollie)', 87670),
(95, 4, 'Nhắm bắn and reloading (loading)', 89960),
(96, 4, 'Chính xác Lewandowski', 92360),
(97, 4, 'First class bay trên Boeing (first class bay trên Boeing)', 94710),
(98, 4, 'Chất xám trên con Rollie (chất xám trên con Rollie)', 97040),
(99, 4, 'Nhắm bắn and reloading (nhắm bắn and reloading)', 99320),
(100, 4, 'Chính xác Lewandowski (chính xác Lewandowski)', 101680),
(101, 4, 'Go, go, go, go!', 103510),
(102, 4, 'Năm sau có nhiều KPI (way)', 104150),
(103, 4, 'Mua con xe hay là thêm cái nhà? (Haizz)', 106280),
(104, 4, 'Toàn hit, cho ra thêm mấy bài? (Thêm đi)', 108530),
(105, 4, 'Everybody, yeah, we go, go, go, go, go, go, go', 110890),
(106, 4, 'Khi mày xay tí xoài (ey)', 113850),
(107, 4, 'Anh đạt tất cả KPI (easy)', 115560),
(108, 4, 'Trường phát như là 89', 118110),
(109, 4, 'Cho anh thấy cánh tay mày đâu? Đâu? Đâu? Đâu? Đâu?', 120480),
(110, 4, 'Và chưa được xi nhê (chưa, chưa)', 123070),
(111, 4, 'Hay tương lai đóng thêm vài ba bộ xi-nê? (Cinema)', 124590),
(112, 4, 'Kiếm người đẹp như Shakira, gọi anh là Piqué', 126710),
(113, 4, 'Và lúc nào anh cũng băng giá bởi vì quần bò ở trong ngăn đá còn nhiều hơn fillet (ice, ice)', 129100),
(114, 4, 'Cầm lên cây bút và như tính toán, năm sau sẽ có một album', 132430),
(115, 4, 'Đưa chất xám, tạo ra output, thu hút thêm fan là outcome', 134890),
(116, 4, 'Nên ăn mừng bằng mục tiêu mới, treo lên Piñata (Piñata)', 137160),
(117, 4, 'Vung, vung một gậy mọi thứ rơi ra, Balenciaga, ay', 139220),
(118, 4, 'Danh hão hay gái gú càng không', 142330),
(119, 4, 'Nhắm tới một chỗ đứng, niềm tự hào đàng trong', 144350),
(120, 4, 'Vì như Thành Long mỗi khi lên đấu và tao luôn chiến thắng', 146500),
(121, 4, 'Bị bắt quả tang yêu sự cố gắng nhưng mà ngoại tình với thành công', 148710),
(122, 4, 'First class bay trên Boeing (first class bay trên Boeing)', 150440),
(123, 4, 'Chất xám trên con Rollie (chất xám trên con Rollie)', 152910),
(124, 4, 'Nhắm bắn and reloading (nhắm bắn and reloading)', 155250),
(125, 4, 'Chính xác Lewandowski (chính xác Lewandowski)', 157520),
(126, 4, 'First class bay trên Boeing (first class bay trên Boeing)', 159980),
(127, 4, 'Chất xám trên con Rollie (chất xám trên con Rollie)', 162210),
(128, 4, 'Nhắm bắn and reloading (nhắm bắn and reloading)', 164630),
(129, 4, 'Chính xác Lewandowski (chính xác Lewandowski)', 166820),
(130, 4, 'Go, go, go, go!', 168430),
(131, 4, 'Toàn là KPI', 169810),
(132, 4, 'Mua con xe hay là thêm cái nhà? (Haizz)', 171550),
(133, 4, 'Toàn hit, cho ra thêm mấy bài? (Thêm đi)', 173880),
(134, 4, 'Everybody, yeah, we go, go, go, go, go, go, go', 176300),
(135, 4, 'Khi mày xay tí xoài (ey)', 179180),
(136, 4, 'Anh đạt tất cả KPI (easy)', 180880),
(137, 4, 'Trường phát như là 89 (ah, yeah)', 183260),
(138, 4, 'Cho anh thấy cánh tay mày đâu? Đâu? Đâu? Đâu? Đâu?', 185440),
(139, 6, 'Mẹ à, lần đầu con biết cảm giác trái tim tan vỡ', 25110),
(140, 6, 'Là khi con biết được chuyện bố và mẹ sẽ phải rời xa', 28450),
(141, 6, 'Những âm thanh cãi vã, vẫn vang lên trong ngõ', 31340),
(142, 6, 'Và đó cũng là lần đầu tiên con ghen tị với con nhà người ta', 33960),
(143, 6, 'Ghen tị với thằng bàn bên, có hộp sữa mẹ nhét vào cặp', 36880),
(144, 6, 'Ghen tị với đứa lớp kế, ngồi sau xe mẹ ôm thật chặt', 39880),
(145, 6, 'Nhớ mãi những mâm cơm tối mẹ gắp cho con thịt xơi ngập mặt', 42750),
(146, 6, 'Và con luôn ước giây phút đó sẽ mãi diễn ra như một vòng lặp', 45170),
(147, 6, 'Nhưng mà ước chỉ là ước, giờ mỗi người một nơi', 48070),
(148, 6, 'Con chẳng nói được một lời, bố quay mặt, nước mắt mẹ rơi', 51290),
(149, 6, 'Là khi căng thẳng giữa bố với mẹ sẽ không thể nào mà nguôi', 53930),
(150, 6, 'Rồi thằng lớn ở lại với bố, còn thằng bé thì để mẹ nuôi', 56540),
(151, 6, 'Con nhớ nhất ngày hôm ấy, mẹ nhìn ngoái lại sau', 59790),
(152, 6, 'Từng dòng nước mắt mặn chát, mẹ lấy tay áo mẹ lau', 62670),
(153, 6, 'Trước khi để con một mình sau cánh cửa cùng ảnh gia đình', 65520),
(154, 6, 'Và đó cũng là khoảnh khắc cuối cùng cả nhà mình được đứng cùng nhau (mẹ ơi)', 67980),
(155, 6, 'Mẹ ơi, con mong, mong mẹ về đây', 71830),
(156, 6, 'Mẹ đang đi đâu sao con không thấy?', 74860),
(157, 6, 'Mẹ nói với con đêm nay khi trời khuya sẽ về', 77510),
(158, 6, 'Nhưng sao mãi chưa về?', 81470),
(159, 6, 'Chẳng muốn búp bê hay là gấu bông', 83330),
(160, 6, 'Chẳng muốn siêu nhân hay là trái bóng', 86270),
(161, 6, 'Vì con chỉ cần mẹ về bên con', 88970),
(162, 6, 'Chỉ cần mẹ về bên con', 92620),
(163, 6, 'Con rất nhớ mẹ', 96090),
(164, 6, 'Con đang nhớ lắm', 98730),
(165, 6, 'Mẹ đang ở đâu?', 101630),
(166, 6, 'Mãi chưa về?', 104380),
(167, 6, 'Bố đã có gia đình mới, và mẹ cũng vậy', 106220),
(168, 6, 'Trái Đất thì vẫn cứ quay, còn thời gian vẫn chạy', 109140),
(169, 6, 'Ai cũng có hạnh phúc riêng và phải lo cho nó đầu tiên', 111740),
(170, 6, 'Nên con không trách lỗi do ai khi con phải tự đứng dậy', 114640),
(171, 6, 'Con muốn thấy mẹ ngay để có thể hỏi', 116890),
(172, 6, 'Con đường mẹ đi ngày qua liệu có lấp đầy toàn đá và sỏi?', 119090),
(173, 6, 'Liệu vất vả chốn phốn hoa, đáng níu chặt hay bỏ qua?', 121900),
(174, 6, 'Có giúp mẹ tìm được hạnh phúc hay chỉ khiến mẹ càng thêm mệt mỏi?', 124570),
(175, 6, 'Tuy buồn vì mẹ không thể tới', 127560),
(176, 6, 'Để nhìn thằng con trai mẹ bước từng bước trên hành trình mới', 129140),
(177, 6, 'Con vẫn sẽ giữ mình sạch kể cả lúc cái bụng mình đói', 131840),
(178, 6, 'Vẫn sẽ nghe lời mẹ dạy dù là những hành động giản đơn', 134740),
(179, 6, 'Thay thế cho những lời cảm ơn mà bản thân con không thể nói', 137330),
(180, 6, 'Con giờ đã lớn hơn, chẳng ngại làm việc khó', 140670),
(181, 6, 'Nhưng không thể thay đổi việc mẹ đã xa theo nơi chiều gió', 143290),
(182, 6, 'Biết mẹ sẽ cười thật tươi vì những điều mà con làm được', 146210),
(183, 6, 'Nhưng thật tiếc, mẹ không ở đây để cho con thấy vẻ tự hào đó', 148840),
(184, 6, 'Mẹ ơi, con mong, mong mẹ về đây', 152000),
(185, 6, 'Mẹ đang đi đâu sao con không thấy?', 154680),
(186, 6, 'Mẹ nói với con đêm nay khi trời khuya sẽ về', 157560),
(187, 6, 'Nhưng sao mãi chưa về?', 161370),
(188, 6, 'Chẳng muốn búp bê hay là gấu bông', 163170),
(189, 6, 'Chẳng muốn siêu nhân hay là trái bóng', 166040),
(190, 6, 'Vì con chỉ cần mẹ về bên con', 169010),
(191, 6, 'Chỉ cần mẹ về bên con', 172530),
(192, 6, 'Mẹ ơi, con mong, mong mẹ về đây (đây)', 174610),
(193, 6, 'Mẹ đang đi đâu sao con không thấy? (Thấy)', 177630),
(194, 6, 'Mẹ nói với con đêm nay khi trời khuya sẽ về', 180540),
(195, 6, 'Nhưng sao mãi chưa về? (Về)', 184240),
(196, 6, 'Chẳng muốn búp bê hay là gấu bông (gấu bông)', 186150),
(197, 6, 'Chẳng muốn siêu nhân hay là trái bóng (trái bóng)', 189010),
(198, 6, 'Vì con chỉ cần mẹ về bên con (về bên con)', 191970),
(199, 6, 'Chỉ cần mẹ về bên con', 195480),
(200, 6, 'Con rất nhớ mẹ', 198970),
(201, 6, 'Con đang nhớ lắm', 201600),
(202, 6, 'Mẹ đang ở đâu?', 204580),
(203, 6, 'Mãi chưa về?', 207450);

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
(6, 'Nhạc đi tắm', 'QSZqSss49HOyRaPzQymitz8P2942'),
(7, 'Here Where Go', 'QSZqSss49HOyRaPzQymitz8P2942'),
(33, 'Nhạc chơi game', 'QSZqSss49HOyRaPzQymitz8P2942'),
(42, 'abc', 'XMe0wL5W9wMYZEpdae30GwG15JD3'),
(51, 'Rap', 'NrZk7AA9LmfTQF1t3CbkQgeGYVh1'),
(52, 'rap', '5M0tL4Wx4DbSyoy50BtML8eNoqY2'),
(53, 'abc', '5M0tL4Wx4DbSyoy50BtML8eNoqY2'),
(55, 'ra', 'ftQTNjkDsbg7EzgUtCfKUOZFm0i1'),
(56, 'b', 'ftQTNjkDsbg7EzgUtCfKUOZFm0i1'),
(59, 'a', 'pLWxZlIzKjTGVseUq9Iek03ioNf2'),
(62, 'Rap', 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3'),
(63, 'Nhac di tam', 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `playlist_user_love`
--

CREATE TABLE `playlist_user_love` (
  `playlist_user_love_id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `playlist_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `playlist_user_love`
--

INSERT INTO `playlist_user_love` (`playlist_user_love_id`, `user_id`, `playlist_id`) VALUES
(25, 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3', 4),
(24, 'NrZk7AA9LmfTQF1t3CbkQgeGYVh1', 2),
(2, 'ZCt6n9uDFjZ9lOOtPataEGxngfr2', 5);

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
(3, 2, 7),
(8, 6, 4),
(13, 6, 6),
(14, 7, 4),
(12, 33, 5),
(15, 51, 1),
(16, 51, 7);

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
(1, 'Chạy Ngay Đi', 'https://upload.wikimedia.org/wikipedia/vi/thumb/8/85/Chay_ngay_di.png/220px-Chay_ngay_di.png', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716820665/music/ChayNgayDi-SonTungMTP-5468704_xdbnmx.mp3', 9, 27, 1, 100, 1),
(2, 'Buồn Hay Vui (Feat. Rpt Mck, Obito & Ronboogz) ', 'https://photo-resize-zmp3.zadn.vn/w600_r1x1_jpeg/cover/6/d/a/a/6daaea74ed8423b2a0e769011d6d3eb3.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716821863/music/BuonHayVuiFeatRptMckObitoRonboogz-VSOULRPTMCKObitoRonboogz-13159599_hngovf.mp3', 11, 29, 1, 80, 1),
(3, 'Bảo Tàng', 'https://i1.sndcdn.com/artworks-GjgUcervqHm9-0-t500x500.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716821865/music/BaoTang-LowG-12625032_hegemq.mp3', 13, 29, 1, 57, 1),
(4, 'KPI', 'https://photo-resize-zmp3.zadn.vn/w360_r1x1_jpeg/avatars/c/2/a/1/c2a154106353063625c9412ed20443a0.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716821881/music/KPI-HIEUTHUHAI-11966370_jn3qtt.mp3', 12, 18, 4, 34, 0),
(5, 'Simp Gái 808', 'https://i.ytimg.com/vi/d2Jb2LgAsYw/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDLzfDXYR1uPb8N60QDnLvKs1YpbA', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716821915/music/SimpGai808-LowG-11976517_kgirbn.mp3', 13, 29, 5, 35, 0),
(6, 'Thư Gửi Mẹ', 'https://i.ytimg.com/vi/KZYAW1plg_M/maxresdefault.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716821893/music/ThuGuiMe-Tez-13565635_vucb06.mp3', 14, 30, 5, 54, 0),
(7, 'Đừng Làm Nó Phức Tạp', 'https://bazaarvietnam.vn/wp-content/uploads/2024/01/Harpers-Bazaar-tlinh-mo-bat-2024-voi-MV-dung-lam-no-phuc-tap_03.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716822066/music/DungLamNoPhucTap-tlinh-13521882_wxypgw.mp3', 15, 29, 5, 54, 0),
(8, 'Chị Ngã Em Nâng', 'https://i1.sndcdn.com/artworks-000441954789-d6haiq-t500x500.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716823020/music/ChiNgaEmNang-BichPhuong-5758252_rmwglm.mp3', 16, 11, 3, 43, 0),
(9, 'Tình Yêu Giữa Mùa Đông', 'https://photo-resize-zmp3.zmdcdn.me/w256_r1x1_jpeg/avatars/3/e/3e602dd0ab83a3a8c3f32309bb9a88f9_1460456703.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716823385/music/TinhYeuGiuaMuaDong-PhanManhQuynh-3700927_lxcpuq.mp3', 8, 13, 4, 100, 0),
(10, 'Vợ Người Ta', 'https://avatar-ex-swe.nixcdn.com/song/2018/05/10/6/5/d/f/1525966962511_640.jpg', 'https://res.cloudinary.com/dgdb5znxn/video/upload/v1716823253/music/VoNguoiTa-PhanManhQuynh-4109355_gjhkwn.mp3', 8, 22, 2, 30, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `song_again`
--

CREATE TABLE `song_again` (
  `song_again_id` int(11) NOT NULL,
  `date_listen` datetime NOT NULL DEFAULT current_timestamp(),
  `song_id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `song_again`
--

INSERT INTO `song_again` (`song_again_id`, `date_listen`, `song_id`, `user_id`) VALUES
(17, '2024-05-23 15:28:46', 3, 'QSZqSss49HOyRaPzQymitz8P2942'),
(18, '2024-05-23 15:29:46', 4, 'QSZqSss49HOyRaPzQymitz8P2942'),
(19, '2024-05-23 15:31:21', 6, 'QSZqSss49HOyRaPzQymitz8P2942'),
(20, '2024-05-23 15:50:44', 7, 'QSZqSss49HOyRaPzQymitz8P2942'),
(21, '2024-05-23 15:53:20', 8, 'QSZqSss49HOyRaPzQymitz8P2942'),
(25, '2024-05-23 16:49:07', 9, 'QSZqSss49HOyRaPzQymitz8P2942'),
(26, '2024-05-23 16:50:03', 10, 'QSZqSss49HOyRaPzQymitz8P2942'),
(27, '2024-05-23 16:51:29', 1, 'QSZqSss49HOyRaPzQymitz8P2942'),
(28, '2024-05-23 16:51:49', 2, 'QSZqSss49HOyRaPzQymitz8P2942'),
(29, '2024-05-23 16:56:38', 5, 'QSZqSss49HOyRaPzQymitz8P2942'),
(30, '2024-05-27 15:48:35', 7, 'NrZk7AA9LmfTQF1t3CbkQgeGYVh1'),
(31, '2024-05-27 17:40:32', 3, 'NrZk7AA9LmfTQF1t3CbkQgeGYVh1'),
(32, '2024-05-30 16:08:09', 2, 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3'),
(33, '2024-05-30 16:20:09', 3, 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3'),
(34, '2024-05-30 16:20:59', 4, 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3');

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
(143, 'boCsxQUcmfNsvWQ7CU8SSsbSmkA3', 4),
(135, 'QSZqSss49HOyRaPzQymitz8P2942', 1),
(126, 'QSZqSss49HOyRaPzQymitz8P2942', 4);

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
('2LKuuAWZX7W1iQk0Fy8qakkoLde2'),
('3i0hJyaL2kNBKjLrT8t8uPhxsa32'),
('47FfQjwGwXfk0CLI7uOTNRMRdjH3'),
('5M0tL4Wx4DbSyoy50BtML8eNoqY2'),
('7G4ZjOr8IRT5QnTLvoocHBNrwS62'),
('7oEPyI8hGqUB45vZNkMv3tsWahB2'),
('aeBg8o0lO7aEeGsqyWqvaMhZvu13'),
('boCsxQUcmfNsvWQ7CU8SSsbSmkA3'),
('Clgz1Ye9WXeXg4Qk3ZP9exz0ROo1'),
('EILuLROK3rf5SbsMK1nOKr2R1713'),
('Fcdj4N26i8PpzzpQCFFSqw5BHLx2'),
('ftQTNjkDsbg7EzgUtCfKUOZFm0i1'),
('GxUfF3C40NeUQsrJhdJeTD4xWxx1'),
('HU0KmfuPLUXvFKgmghtkRoiyNif1'),
('Jy5byXeAJ2aEbTCuF0o4EOPId3q2'),
('Kct8HgBtVsaLhXE5J8FBfMPEFWr2'),
('LrBLAMC8kQfNhBP2dq0PTnrAOEz2'),
('mFjqHF12ifQh8t0bOVL0NkeyMIf1'),
('N10KwXknfkUdhg7dyl9bKgMwVXc2'),
('NrZk7AA9LmfTQF1t3CbkQgeGYVh1'),
('pLWxZlIzKjTGVseUq9Iek03ioNf2'),
('PTUouSNklLYkEpiTExqaJ08zpgQ2'),
('QRig9dL8kaYE02jFwnpAeKAFXNd2'),
('QSZqSss49HOyRaPzQymitz8P2942'),
('tbWSmMgy1Uc4igHWLkA2WANhhUp2'),
('tlYMgkr1HMhR81FusghOksELJKl1'),
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
-- Chỉ mục cho bảng `follow`
--
ALTER TABLE `follow`
  ADD PRIMARY KEY (`follow_id`),
  ADD KEY `user_id` (`user_id`,`artist_id`),
  ADD KEY `artist_id` (`artist_id`);

--
-- Chỉ mục cho bảng `lyric`
--
ALTER TABLE `lyric`
  ADD PRIMARY KEY (`lyric_id`),
  ADD KEY `song_id` (`song_id`);

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
  ADD PRIMARY KEY (`playlist_user_love_id`),
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
-- AUTO_INCREMENT cho bảng `follow`
--
ALTER TABLE `follow`
  MODIFY `follow_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT cho bảng `lyric`
--
ALTER TABLE `lyric`
  MODIFY `lyric_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=204;

--
-- AUTO_INCREMENT cho bảng `playlist`
--
ALTER TABLE `playlist`
  MODIFY `playlist_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `playlist_user`
--
ALTER TABLE `playlist_user`
  MODIFY `playlist_user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT cho bảng `playlist_user_love`
--
ALTER TABLE `playlist_user_love`
  MODIFY `playlist_user_love_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT cho bảng `playlist_user_song`
--
ALTER TABLE `playlist_user_song`
  MODIFY `playlist_user_song_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

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
  MODIFY `song_again_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT cho bảng `song_love`
--
ALTER TABLE `song_love`
  MODIFY `song_love_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=144;

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
-- Các ràng buộc cho bảng `follow`
--
ALTER TABLE `follow`
  ADD CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `lyric`
--
ALTER TABLE `lyric`
  ADD CONSTRAINT `lyric_ibfk_1` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE;

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
