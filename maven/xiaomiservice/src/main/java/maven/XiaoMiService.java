package maven;

public class XiaoMiService {


    public String xiaomiService() {
        //希望调用DAO能力
        XiaoMiDAO dao = new XiaoMiDAO();
        String res = dao.addXiaoMi();
        return "小米服务"+res;
    }
    //测试
    public static void main(String[] args) {
        XiaoMiService xiaoMiService = new XiaoMiService();
        String service = xiaoMiService.xiaomiService();
        System.out.println(service);
    }
}
