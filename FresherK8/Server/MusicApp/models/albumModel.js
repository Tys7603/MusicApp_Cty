

const queryDatabase = require("../database/database.js")

const getListAlbumNew= async () => {
  const query = "SELECT album_id, album_name, album_image, name_artist "+
  "FROM ALBUM " +
  "WHERE album_new = 1"
  return await queryDatabase(query)
}

const getListAlbumLove = async () => {
  const query = "SELECT a.album_id , a.album_name , a.album_image , a.name_artist, COUNT(al.album_love_id) AS total_likes " +
  "FROM album a " +
  "JOIN song s ON a.album_id = s.album_id " +
  "JOIN album_love al ON s.song_id = al.song_id " +
  "GROUP BY a.album_id " +
  "ORDER BY total_likes DESC " +
  "LIMIT 5"
  return await queryDatabase(query)
}
module.exports = {
    getListAlbumNew,
    getListAlbumLove
}

