const queryDatabase = require("../database/database.js")

const getListTopicAndCategory = async () => {
  const queryTopic = "SELECT * FROM topic"
  const queryCategory = "SELECT * FROM category"
  try {
    // Sử dụng Promise.all để thực hiện cả hai truy vấn cùng một lúc
    const [topics, categories] = await Promise.all([
        
      queryDatabase(queryTopic),

      queryDatabase(queryCategory)
    ]);

    return { topics, categories };

  } catch (error) {

    throw new Error("Failed to fetch topics and categories: " + error.message);

  }

}
module.exports = {
    getListTopicAndCategory
}
