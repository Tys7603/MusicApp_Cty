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

  const query = "SELECT plu.playlist_user_id, plu.playlist_user_name, a.name_artist, s.song_image " +
    "FROM playlist_user_song as pus " +
    "RIGHT JOIN Song as s ON s.song_id  = pus.song_id " +
    "RIGHT JOIN Album as a ON s.album_id = a.album_id " +
    "RIGHT JOIN playlist_user as plu ON pus.playlist_user_id = plu.playlist_user_id " +
    "WHERE plu.user_id = ? "

  return await queryDatabase(query, [userId])
}


module.exports = {
  getListPlaylist,
  getListPlaylistMoodToday,
  createPlaylistUser,
  getListPlaylistByIdUser
}
