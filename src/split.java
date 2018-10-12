import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyangbd on 2018/9/21.
 */
public class split {
	public static void main(String[] args) {
		String tem = "settle_client,tran_type,tran_date,tran_time,LINKED_AGGR_TYPE,LINKED_ACCT_SYS,settle_bank_flag,base_acct_no,settle_acct_ccy,settle_acct_seq_no,ACCT_SYS,settle_branch,settle_bank_name,acct_class,settle_base_acct_no,ccy,acct_seq_no,settle_mobile_phone,acct_name,IN_BASE_ACCT_NO,IN_ACCT_SEQ_NO,IN_BASE_CCY";

		String[] tem1 = tem.split(",");
		List list = new ArrayList();
		list = java.util.Arrays.asList(tem1);
		for (Object a :list){
			System.out.println(a);
		}
	}
}
