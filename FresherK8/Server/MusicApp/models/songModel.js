const queryDatabase = require("../database/database.js")

const getListSong = async () => {
  const query = "SELECT s.name, s.image, s.url, a.name_artist FROM Song as s " +
    "INNER JOIN Album as a ON s.album_id = a.id"
  return await queryDatabase(query)
}
module.exports = {
  getListSong
}