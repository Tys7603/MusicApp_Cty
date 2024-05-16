const queryDatabase = require("../database/database.js")

const getLyricsBySongId= async (song_id) => {
  const query = "SELECT lyric_text, startMs "+
  "FROM LYRIC " +
  "WHERE song_id = ? " + 
  "ORDER BY startMs ASC"
  return await queryDatabase(query, [song_id])
}

module.exports = {
    getLyricsBySongId
}

