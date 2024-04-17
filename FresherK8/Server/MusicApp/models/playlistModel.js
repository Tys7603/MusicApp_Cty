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
module.exports = {
  getListPlaylist,
  getListPlaylistMoodToday
}
