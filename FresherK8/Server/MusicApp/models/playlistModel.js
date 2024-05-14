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

// xoa playlist user

const deletePlaylistUserById = async (playlistUserId) => {


  console.log(playlistUserId)

  const query = "DELETE FROM `playlist_user` WHERE `playlist_user_id` IN (?);"
  
  return await queryDatabase(query, [playlistUserId])
}


module.exports = {
  getListPlaylist,
  getListPlaylistMoodToday,
  createPlaylistUser,
  getListPlaylistByIdUser,
  deletePlaylistUserById
}
