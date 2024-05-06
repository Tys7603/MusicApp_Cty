const queryDatabase = require("../database/database.js")

const creadUser = async (userId) => {
  const query = "INSERT INTO User(user_id) VALUE (?)"
  return await queryDatabase(query, [userId])
}



module.exports = {
    creadUser
}
