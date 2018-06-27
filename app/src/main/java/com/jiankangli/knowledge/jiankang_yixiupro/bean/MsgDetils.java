package com.jiankangli.knowledge.jiankang_yixiupro.bean;


public class MsgDetils {
	public String code;
	public String msg;
	public String time;
	public Data data;
	public class Data{
		public String content;
		public String egginerName;
		public String egginerUserId;
		public String id;
		public String readFlag;
		public String sendTime;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getEgginerName() {
			return egginerName;
		}

		public void setEgginerName(String egginerName) {
			this.egginerName = egginerName;
		}

		public String getEgginerUserId() {
			return egginerUserId;
		}

		public void setEgginerUserId(String egginerUserId) {
			this.egginerUserId = egginerUserId;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getReadFlag() {
			return readFlag;
		}

		public void setReadFlag(String readFlag) {
			this.readFlag = readFlag;
		}

		public String getSendTime() {
			return sendTime;
		}

		public void setSendTime(String sendTime) {
			this.sendTime = sendTime;
		}

		public String getSendUserId() {
			return sendUserId;
		}

		public void setSendUserId(String sendUserId) {
			this.sendUserId = sendUserId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUseFlag() {
			return useFlag;
		}

		public void setUseFlag(String useFlag) {
			this.useFlag = useFlag;
		}

		public String sendUserId;
		public String type;
		public String useFlag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
