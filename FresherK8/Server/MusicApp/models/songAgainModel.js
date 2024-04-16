const connection = require("../database/database.js")

const insertUser = async () => {
  const query = "INSERT INTO NguoiDung(id) VALUE (?)";
  return await connection.queryDatabase(query, [id])
}
module.exports = {
  insertUser
}