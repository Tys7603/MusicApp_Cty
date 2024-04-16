const queryDatabase = require("../database/database.js")

const getListPlaylist = async () => {
  const query = "SELECT * FROM playlist"
  return await queryDatabase(query)
}
module.exports = {
  getListPlaylist
}
