
const model = require('../models/categoryModel.js')

const getListCategoryController = async (req, res) => {

  try {
    const categories = await model.getListCategory()

    res.json({ status: 200 , categories  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
  getListCategoryController
}
