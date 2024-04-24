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
  "WHERE mood_today = 1"

  return await queryDatabase(query)
}

module.exports = {
  getListPlaylist,
  getListPlaylistMoodToday,
}
