const queryDatabase = require("../database/database.js")

const getListTopic = async () => {
  const queryTopic = "SELECT * FROM topic"
  return await queryDatabase(queryTopic)
}

const getListTopicByIdCategory = async (categoryId) => {
  const queryCategory = "SELECT * FROM topic " +
  "WHERE category_id = ? "
  return await queryDatabase(queryCategory, [categoryId])
}

module.exports = {
    getListTopic,
    getListTopicByIdCategory
}
