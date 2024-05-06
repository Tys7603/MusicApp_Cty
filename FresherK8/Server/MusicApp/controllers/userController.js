
const model = require('../models/userModel.js')

const createUserController = async (req, res) => {

  try {
    const { userId } = req.body
    console.log(userId)
    const user = await model.creadUser(userId)

    res.json({ status: 200 , user  })

  } catch (error) {
    res.json({ status: "400", error : error.message });
  }
}

module.exports = {
  createUserController
}
