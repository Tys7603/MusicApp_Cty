

const queryDatabase = require("../database/database.js")

const getListAlbumNew= async () => {
  const query = "SELECT album_id, album_name, album_image, name_artist "+
  "FROM ALBUM " +
  "WHERE album_new = 1"
  return await queryDatabase(query)
}
module.exports = {
    getListAlbumNew
}

