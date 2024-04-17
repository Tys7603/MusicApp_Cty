const queryDatabase = require("../database/database.js")

const getListSongAgain = async (id) => {
  const query = "SELECT sa.song_again_id, s.song_name, s.song_image, s.song_url FROM SONG_AGAIN as sa " +
  "INNER JOIN SONG AS s ON sa.song_id = s.song_id " +
  "WHERE sa.user_id = ?"
  return await queryDatabase(query, [id])
}
module.exports = {
  getListSongAgain
}