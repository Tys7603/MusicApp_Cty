const queryDatabase = require("../database/database.js")

const getListPlaylist = async () => {

  const query = "SELECT s.playlist_id, pl.playlist_name, pl.playlist_image, a.name_artist " +
    "FROM Song as s " +
    "INNER JOIN Playlist as pl ON s.playlist_id = pl.playlist_id " +
    "INNER JOIN Album as a ON s.album_id = a.album_id "

  return await queryDatabase(query)
}

const getListPlaylistMoodToday = async () => {

  const query = "SELECT s.playlist_id, pl.playlist_name, pl.playlist_image, a.name_artist " +
    "FROM Song as s " +
    "INNER JOIN Playlist as pl ON s.playlist_id = pl.playlist_id " +
    "INNER JOIN Album as a ON s.album_id = a.album_id " +
    "WHERE mood_today = 1 " +
    "GROUP BY s.playlist_id "

  return await queryDatabase(query)
}

// add playlist user

const createPlaylistUser = async (namePlaylist, userId) => {

  const query = "INSERT INTO playlist_user(playlist_user_name, user_id) VALUES (?,?)"

  return await queryDatabase(query, [namePlaylist, userId])
}

// playlisst user  by id user

const getListPlaylistByIdUser = async (userId) => {

  const query = "SELECT plu.playlist_user_id, plu.playlist_user_name, COUNT(s.song_id) AS song_count, s.song_image " +
    "FROM playlist_user_song as pus " +
    "RIGHT JOIN Song as s ON s.song_id  = pus.song_id " +
    "RIGHT JOIN Album as a ON s.album_id = a.album_id " +
    "RIGHT JOIN playlist_user as plu ON pus.playlist_user_id = plu.playlist_user_id " +
    "WHERE plu.user_id = ? " +
    "GROUP BY plu.playlist_user_id " +
    "ORDER BY song_count DESC "

  return await queryDatabase(query, [userId])
}

// Xóa playlist người dùng
const deletePlaylistUserById = async (playlistUserId) => {

  const query = "DELETE FROM `playlist_user` WHERE `playlist_user_id` IN (?);"

  return await queryDatabase(query, [playlistUserId])
}

// Xóa playlist yêu thích
const deletePlaylistLoveById = async (playlistLoveId) => {

  const query = "DELETE FROM `playlist_user_love` WHERE `playlist_user_love_id` IN (?);"

  return await queryDatabase(query, [playlistLoveId])
}

// Thêm bài hát vào playlist người dùng
const createSongIntoPlaylistByUserId = async (playlistUserId, songId) => {

  const querySelect = "SELECT * FROM playlist_user_song " +
    "WHERE playlist_user_id = ? AND song_id = ?"

  const queryCreate = "INSERT INTO playlist_user_song(playlist_user_id, song_id) VALUES(?, ?)"

  try {
    // Bắt đầu giao dịch
    await queryDatabase("START TRANSACTION");

    // Thực hiện truy vấn 
    const results = await queryDatabase(querySelect, [playlistUserId, songId]);

    if (results.length > 0) {
      await queryDatabase("ROLLBACK");
      return { status: 409 };
    } else {
      await queryDatabase(queryCreate, [playlistUserId, songId]);
      await queryDatabase("COMMIT");
      return { status: 200 };
    }

  } catch (error) {
    // Rollback giao dịch nếu có lỗi
    await queryDatabase("ROLLBACK");
    // Trả về kết quả lỗi và thông điệp lỗi
    return { status: 400, message: error.message };
  }
}

// Lấy danh sách playlist yêu thích
const getListPlaylistLoveByIdUser = async (userId) => {

  const query = "SELECT s.playlist_id, pl.playlist_name, pl.playlist_image, a.name_artist, plul.playlist_user_love_id " +
    "FROM Song as s " +
    "INNER JOIN Playlist as pl ON s.playlist_id = pl.playlist_id " +
    "INNER JOIN Playlist_user_love as plul ON plul.playlist_id = pl.playlist_id " +
    "INNER JOIN Album as a ON s.album_id = a.album_id " +
    "WHERE plul.user_id = ?"

  return await queryDatabase(query, [userId])
}

// Thêm bài hát vào playlist người dùng
const inserPlaylistIntoPlaylistLoveByUserId = async (playlistId, userId) => {

  const querySelect = "SELECT * FROM playlist_user_love " +
    "WHERE user_id = ? AND playlist_id = ?"

  const queryCreate = "INSERT INTO playlist_user_love(user_id, playlist_id) VALUES(?, ?)"

  try {
    // Bắt đầu giao dịch
    await queryDatabase("START TRANSACTION");

    // Thực hiện truy vấn 
    const results = await queryDatabase(querySelect, [userId, playlistId]);

    if (results.length > 0) {
      await queryDatabase("ROLLBACK");
      return { status: 409 };
    } else {
      await queryDatabase(queryCreate, [userId, playlistId]);
      await queryDatabase("COMMIT");
      return { status: 200 };
    }

  } catch (error) {
    // Rollback giao dịch nếu có lỗi
    await queryDatabase("ROLLBACK");
    // Trả về kết quả lỗi và thông điệp lỗi
    return { status: 400, message: error.message };
  }
}


module.exports = {
  getListPlaylist,
  getListPlaylistMoodToday,
  createPlaylistUser,
  getListPlaylistByIdUser,
  deletePlaylistUserById,
  createSongIntoPlaylistByUserId,
  getListPlaylistLoveByIdUser,
  deletePlaylistLoveById,
  inserPlaylistIntoPlaylistLoveByUserId
}
