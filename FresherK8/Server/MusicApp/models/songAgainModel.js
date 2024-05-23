const queryDatabase = require("../database/database.js")

const getListSongAgain = async (id) => {
  const query = "SELECT sa.song_again_id, s.song_id, s.song_name, s.song_image, s.song_url, a.name_artist, sa.date_listen " +
  "FROM SONG_AGAIN as sa " +
  "INNER JOIN SONG AS s ON sa.song_id = s.song_id " +
  "INNER JOIN ALBUM AS a ON s.album_id = a.album_id " +
  "WHERE sa.user_id = ?"
  return await queryDatabase(query, [id])
}

const createSongAgain = async (userId, songId) => {
  // Kiểm tra xem bài hát đã tồn tại cho userId và songId chưa
  let query = "SELECT COUNT(*) as count FROM song_again WHERE user_id = ? AND song_id = ?";
  let result = await queryDatabase(query, [userId, songId]);
  
  if (result[0].count > 0) {
    return { status: 409, message: "This song is already added by the user." };
  }

  // Kiểm tra số lượng bài hát đã tồn tại cho userId
  query = "SELECT song_again_id FROM song_again WHERE user_id = ? ORDER BY song_again_id";
  result = await queryDatabase(query, [userId]);

  if (result.length >= 20) {
    // Xóa bài hát cũ nhất (đầu tiên trong danh sách)
    const oldestSongId = result[0].song_again_id;
    query = "DELETE FROM song_again WHERE song_again_id = ?";
    await queryDatabase(query, [oldestSongId]);
  }

  // Thêm bài hát mới
  query = "INSERT INTO song_again (user_id, song_id) VALUES (?, ?)";
  await queryDatabase(query, [userId, songId]);

  return { status: 200, message: "Song added successfully." };

}

module.exports = {
  getListSongAgain,
  createSongAgain
}