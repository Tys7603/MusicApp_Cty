const queryDatabase = require("../database/database.js")

const getListSong = async () => {
  const query = "SELECT s.song_name, s.song_image, s.song_url, a.name_artist FROM Song as s " +
    "INNER JOIN Album as a ON s.album_id = a.album_id"
  return await queryDatabase(query)
}
module.exports = {
  getListSong
}