const queryDatabase = require("../database/database.js")

const getListSongAgain = async (id) => {
  const query = "SELECT sa.song_again_id, s.song_name, s.song_image, s.song_url, a.name_artist FROM SONG_AGAIN as sa " +
  "INNER JOIN SONG AS s ON sa.song_id = s.song_id " +
  "INNER JOIN ALBUM AS a ON s.album_id = a.album_id " +
  "WHERE sa.user_id = ?"
  return await queryDatabase(query, [id])
}
module.exports = {
  getListSongAgain
}