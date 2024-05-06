
const model = require('../models/topicModel.js')

const getListTopicController = async (req, res) => {

  try {
    const topics= await model.getListTopic()
    res.json({ status: 200, topics  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

const getListCategoryByIdTopicController = async (req, res) => {

  try {
    const {categoryId} = req.params
    const topics = await model.getListTopicByIdCategory(categoryId)

    res.json({ status: 200 , topics  })
  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

module.exports = {
    getListTopicController,
    getListCategoryByIdTopicController
}
