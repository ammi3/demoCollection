// 云函数入口文件
const cloud = require('wx-server-sdk')
const got = require('got')

cloud.init()

// 云函数入口函数
exports.main = async (event, context) => {
  const getResponse = await got('https://wallhaven.cc/api/v1/search');
  return getResponse.body;
  //const wxContext = cloud.getWXContext()

  // return {
  //   event,
  //   openid: wxContext.OPENID,
  //   appid: wxContext.APPID,
  //   unionid: wxContext.UNIONID,
  // }

  // return await got('https://wallhaven.cc/api/v1/search').body;
}