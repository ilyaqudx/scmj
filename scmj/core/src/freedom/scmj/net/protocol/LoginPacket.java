package freedom.scmj.net.protocol;
public class LoginPacket{
		private int loginType;
		private long userId;
		private String password;
		private String imei;
		private String email;
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public int getLoginType() {
			return loginType;
		}
		public void setLoginType(int loginType) {
			this.loginType = loginType;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		@Override
		public String toString() {
			return "Request [password=" + password + ", imei=" + imei
					+ ", email=" + email + "]";
		}
	}