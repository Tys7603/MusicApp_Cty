const queryDatabase = require("../database/database.js")

const getListPlaylist = async () => {

  const query = "SELECT * FROM playlist"

  return await queryDatabase(query)
}

const getListPlaylistMoodToday = async () => {

  const query = "SELECT * FROM playlist " +
  "WHERE mood_today = 1"

  return await queryDatabase(query)
}

const getListPlaylistById = async (id) => {

  const query = "SELECT s.song_id, s.song_name, s.song_image, s.song_url,  "
  "FROM Song as s " +
  "INNER JOIN Playlist as pl ON s.playlist_id = pl.playlist_id "+
  "WHERE s.playlist_id = ?"

  return await queryDatabase(query, [id])
}
module.exports = {
  getListPlaylist,
  getListPlaylistMoodToday,
  getListPlaylistById
}
