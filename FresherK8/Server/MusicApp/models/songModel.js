const queryDatabase = require("../database/database.js")

const getListSong = async () => {
  const query = "SELECT s.song_id, s.song_name, s.song_image, s.song_url, a.name_artist " +
    "FROM Song as s " +
    "INNER JOIN Album as a ON s.album_id = a.album_id"
  return await queryDatabase(query)
}

const getListSongByPlaylistId = async (playlistId) => {

  const query = "SELECT s.song_id, s.song_name, s.song_image, s.song_url, a.name_artist, s.download " +
    "FROM Song as s " +
    "INNER JOIN Playlist as pl ON s.playlist_id = pl.playlist_id " +
    "INNER JOIN Album as a ON s.album_id = a.album_id " +
    "WHERE s.playlist_id = ?"

  return await queryDatabase(query, [playlistId])
}

const getListSongByTopicId = async (topicId) => {

  const query = "SELECT s.song_id, s.song_name, s.song_image, s.song_url, a.name_artist, s.download " +
    "FROM Song as s " +
    "INNER JOIN Topic as t ON s.topic_id = t.topic_id " +
    "INNER JOIN Album as a ON s.album_id = a.album_id " +
    "WHERE t.topic_id = ?"

  return await queryDatabase(query, [topicId])
}

module.exports = {
  getListSong,
  getListSongByPlaylistId,
  getListSongByTopicId
}