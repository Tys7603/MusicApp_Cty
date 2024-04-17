
const model = require('../models/topicModel.js')

const getListTopicController = async (req, res) => {

  try {
    const topics= await model.getListTopic()
    res.json({ status: 200, topics  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
    getListTopicController
}
