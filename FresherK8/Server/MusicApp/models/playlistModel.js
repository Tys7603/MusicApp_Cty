const queryDatabase = require("../database/database.js")

const getListPlaylist = async () => {

  const query = "SELECT s.playlist_id, pl.playlist_name, pl.playlist_image, a.name_artist " +
  "FROM Song as s " +
  "INNER JOIN Playlist as pl ON s.playlist_id = pl.playlist_id " +
  "INNER JOIN Album as a ON s.album_id = a.album_id "

  return await queryDatabase(query)
}

const getListPlaylistMoodToday = async () => {

  const query ="SELECT s.playlist_id, pl.playlist_name, pl.playlist_image, a.name_artist " +
  "FROM Song as s " +
  "INNER JOIN Playlist as pl ON s.playlist_id = pl.playlist_id " +
  "INNER JOIN Album as a ON s.album_id = a.album_id "+
  "WHERE mood_today = 1 " +
  "GROUP BY s.playlist_id "

  return await queryDatabase(query)
}

// add playlist love

const createPlaylistLove= async (namePlaylist, userId) => {

  const query ="INSERT INTO playlist_user(playlist_user_name, user_id) VALUES (?,?)" 

  return await queryDatabase(query, [namePlaylist, userId])
}



module.exports = {
  getListPlaylist,
  getListPlaylistMoodToday,
  createPlaylistLove
}
