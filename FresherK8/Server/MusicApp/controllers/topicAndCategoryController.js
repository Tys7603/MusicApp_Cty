
const model = require('../models/topicAndCategoryModel.js')

const getListTopicAndCategoryController = async (req, res) => {

  try {
    const topicAndCategory = await model.getListTopicAndCategory()
    const {topics, categories} = topicAndCategory
    res.json({ status: 200, topics, categories  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
    getListTopicAndCategoryController
}
