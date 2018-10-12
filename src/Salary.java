/**
 * Created by liyangbd on 2018/9/29.
 */
public class Salary {
	public static void main(String[] args) {
		String salaryLevel;
		String flag;

		flag=null;
		salaryLevel="HR";

		//根据flag结合账户工资属性判断是否允许
		if (flag!=null){
			if (salaryLevel==null){
				System.out.println("人力柜员不可以查非工资账户明细");
			}else if (flag.equals("HR")&&salaryLevel!=null&&salaryLevel.equals("GG")){
				System.out.println("非高管人力柜员不可以查高管工资账户明细");
			}
		}else if (flag==null){
			if (salaryLevel!=null){
				System.out.println("非人力柜员不可以查工资账户明细");
			}
		}


			System.out.println("成功查询");

	}
}
