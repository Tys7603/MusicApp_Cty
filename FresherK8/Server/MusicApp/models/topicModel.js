const queryDatabase = require("../database/database.js")

const getListTopic = async () => {
  const queryTopic = "SELECT * FROM topic"
  return await queryDatabase(queryTopic)
}
module.exports = {
    getListTopic
}
