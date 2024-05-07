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

const getListSonglove = async (userId) => {

  const query = "SELECT sl.song_love_id, s.song_id, s.song_name, s.song_image, s.song_url, a.name_artist " +
    "FROM Song as s " +
    "INNER JOIN Album as a ON s.album_id = a.album_id " +
    "INNER JOIN Song_love as sl ON sl.song_id = s.song_id " +
    "WHERE sl.user_id = ?"
    
  return await queryDatabase(query, [userId])
}

const createSongLove = async (userId, songId) => {

  const query = "INSERT INTO song_love (user_id, song_id) VALUES (?, ?)"

  return await queryDatabase(query, [userId, songId])
}

const deleteSongLove = async (songLoveId) => {

  const query = "DELETE FROM song_love WHERE song_love_id = ?"

  return await queryDatabase(query, [songLoveId])
}

module.exports = {
  getListSong,
  getListSongByPlaylistId,
  getListSongByTopicId,
  createSongLove,
  deleteSongLove,
  getListSonglove
}