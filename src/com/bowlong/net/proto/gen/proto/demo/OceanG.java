package com.bowlong.net.proto.gen.proto.demo;

import static com.bowlong.net.proto.gen.B2G.CLIENT;
import static com.bowlong.net.proto.gen.B2G.DATA;
import static com.bowlong.net.proto.gen.B2G.SERVER;

import java.util.List;
import java.util.Map;

import com.bowlong.io.FileEx;
import com.bowlong.net.proto.gen.B2Class;
import com.bowlong.net.proto.gen.B2Field;
import com.bowlong.net.proto.gen.B2Method;
import com.bowlong.net.proto.gen.B2Param;
import com.bowlong.net.proto.gen.Bio2GCSharp;
import com.bowlong.net.proto.gen.Bio2GJava;

@B2Class(namespace = "ocean")
public class OceanG {
	@B2Class(type = DATA, remark = "Global" , constant = true)
	class Global {
		@B2Field(def = "123", remark= "val1")
		public int TYPE_D1;
		@B2Field(def = "456", remark= "val2")
		public int TYPE_D2;
		@B2Field(def = "222", remark= "val3")
		public int TYPE_D3;

		@B2Field(def = "d1", remark= "d1")
		public String STR_D1;
		@B2Field(def = "d2", remark= "d2")
		public String STR_D2;
	}

	// ///////////////////////////////////////////////////
	@B2Class(type = DATA, remark = "out boolVal")
	class OutBoolVal {
		public boolean val;
	}

	@B2Class(type = DATA, remark = "out intVal")
	class OutIntVal {
		public int val;
	}

	@B2Class(type = DATA, remark = "out longVal")
	class OutLongVal {
		public long val;
	}

	@B2Class(type = DATA, remark = "out stringVal")
	class OutStrVal {
		public String val;
	}

	@B2Class(type = DATA, remark = "out bytesVal")
	class OutBytesVal {
		public byte[] val;
	}

	@B2Class(type = DATA, remark = "out stringListVal")
	class OutStrListVal {
		public List<String> val;
	}

	@B2Class(type = DATA, remark = "out doubleVal")
	class OutDoubleVal {
		public double val;
	}

	// ///////////////////////////////////////////////////

	@B2Class(type = DATA, remark = "返回值")
	class ReturnStatus {
		public int succ;
		public String msg;
	}

	@B2Class(type = DATA, remark = "码头信息")
	class Dock {
		public int id;
		public String name;
		public int txid;
		public int userId;
		public String userMz;
		public int userLvl;
		public String unionMz;
		public int portId;
		public int y;
		public int strong;
		public boolean protect;
	}

	@B2Class(type = DATA, remark = "跳动提示")
	class TipItem {
		public int type;
		public String text;
		public int value;
	}

	@B2Class(type = DATA, remark = "排行榜")
	class BillItem {
		public int id;
		public int oid;
		public String s1;
		public String s2;
		public String s3;
	}

	@B2Class(type = DATA, remark = "海盗码头情况")
	class PirateDockItem {
		public int id;
		public String mz;
		public int lvl;
		public int iconId; // 头像id
		public boolean isFinish;
		public boolean isCurrent;
		public boolean isBoss;
		public int power; // 战斗力
		public int captionNum; // 舰队数
		public int tonnage; // 吨位
		public String skills;
		public String formation;
		public String str; // 建议
		public int stroy1;
		public int stroy2;
		public int star; // 成就(评星)
		public List<PirateDockReward> rewards;
		public List<PirateCaptionPos> captionPostion; // 海盗舰长位置
	}

	@B2Class(type = DATA, remark = "海盗码头奖励")
	class PirateDockReward {
		public int id;
		public int type; // 0:道具 1:装备
		public int quality; // 品质
		public int lvl;
		public String str;
	}

	@B2Class(type = DATA, remark = "海盗码头舰长位置")
	class PirateCaptionPos {
		public int ccid;
		public int x;
		public int y;
	}

	@B2Class(type = DATA, remark = "25PP充值订单")
	class OrderItem25PP {
		public int id;
		public double rmb;
		public int gold;
		public String dat;
		public int stat; // -1:失败 0:等待中 1:成功
	}

	@B2Class(type = DATA, remark = "港口状态")
	class PortStatItem {
		public int id;
		public boolean isOpen; // 是否开放
		public boolean isFire; // 是否战斗
		public boolean isSomebody; // 是否有人
	}

	@B2Class(type = DATA, remark = "装备信息")
	public class EquipItem {
		public int id;
		public String icon;
		public String mz; // 名字
		public int lv; // 等级
		public int captainId; // 舰长id
		public int bodyId; // 身体位置
		public Map<String, Integer> attr; // 属性
		public int strongNum; // 强化次数
		public int quality; // 1:白色,2:绿色,3:蓝色,4:紫色
		public int sliver; // 银币
		public boolean isDelete; // 是否被删除
	}

	@B2Class(type = DATA, remark = "道具信息")
	public class PropItem {
		public int id;
		public String mz; // 名字
		public int quality; // 品质
		public int type;
		public int num;
		public String str; // 描述
		public String effect; // 效果
		public int userDjid; // 用户的道具id
		public int icon;
	}

	@B2Class(type = DATA, remark = "邮件信息")
	public class MailItem {
		public int id;
		public int type;
		public int senderId; // 发送者
		public String senderMz;
		public int receiverId; // 接收者
		public String receiverMz;
		public String title;
		public String content;
		public String dat;
		public boolean readed;

		public boolean isPvp;
		public int yhid;
		public String yhmz;
		public int portId;
		public String portMz;
		public int dockId;
		public String dockMz;
	}

	@B2Class(type = DATA, remark = "邮件清单")
	public class Mails {
		List<MailItem> mails;
	}

	@B2Class(type = DATA, remark = "聊天")
	public class MsgItem {
		int type; // 1:公聊 2:公会 3:私聊 4:大喇叭
		int uid;
		String mz;
		String mz2;
		String msg;
		long tm;
	}

	@B2Class(type = DATA, remark = "公会信息")
	public class UnionItem {
		public int id;
		public String name; // 名字
		public String shortName; // 短名字
		public int lvl; // 公会等级
		public int gold; // 公会金币
		public int member; // 成员数量
		public int memberMax; // 最大成员数
		public String desc; // 公会介绍
		public int leaderId; // 会长id
		public String leader; // 会长名字
	}

	@B2Class(type = DATA, remark = "公会信息")
	public class Unions {
		public List<UnionItem> unions;
	}

	@B2Class(type = DATA, remark = "公会成员")
	public class UnionMember {
		public int id;
		public String name; // 名字
		public long score; // 积分
		public String title; // 职位
		public String dat; // 最后登录
		public boolean online;
		public int donationGold; // 募捐金币
	}

	@B2Class(type = DATA, remark = "公会成员")
	public class UnionMembers {
		public List<UnionMember> members;
	}

	@B2Class(type = DATA, remark = "入会申请")
	public class UnionReq {
		public int id;
		public String name;
		public int score;
		public String dat;
	}

	@B2Class(type = DATA, remark = "入会申请")
	public class UnionReqs {
		public List<UnionReq> reqs;
	}

	@B2Class(type = DATA, remark = "公会建筑")
	public class UnionBuilding {
		public int id;
		public String name;
		public int lvl;
		public int nextGold;
		public String desc;
	}

	@B2Class(type = DATA, remark = "公会建筑")
	public class UnionBuildings {
		public List<UnionBuilding> buildings;
	}

	@B2Class(type = DATA, remark = "ID MZ")
	public class IdMzItem {
		public int id;
		public String mz;
	}

	@B2Class(type = DATA, remark = "ID MZ s")
	public class IdMzItems {
		public List<IdMzItem> items;
	}

	@B2Class(type = DATA, remark = "航线信息")
	public class VoyageItem {
		public int captainId;
		public String captainMz;
		public int lvl;
		public int tonnage; // 吨位
		public int type; // 1:我的航线 2:朋友的航线 3:敌人的航线

		public int routeId; // 航线id
		public String departureTime; // 出发时间
		public String arriveTime; // 到达时间

		public int yhid1;
		public String yhmz1;
		public int mtid1;
		public String mtmz1;

		public int yhid2;
		public String yhmz2;
		public int mtid2;
		public String mtmz2;

		public int cmd;
		public int state; // 1.等待开始 2:前进 3:停留中 4:战斗中 5.返航中 6.结束
		public int fightId;
	}

	@B2Class(type = DATA, remark = "商品")
	public class MarketItem {
		public int id;
		public String mz;
		public String str;
		public int gold;
		public int icon;
		public double discount;
	}

	@B2Class(type = DATA, remark = "商品清单")
	public class MarketItems {
		public List<MarketItem> items;
	}

	@B2Class(type = DATA, remark = "收藏夹")
	public class FavoriteItem {
		public int dockId;
		public String dockMz;
		public int yhid;
		public String yhmz;
		public int type;// 0:普通,好友 1:敌人
		public int portId;
		public String portMz;
		public boolean isDelete = false;
	}

	@B2Class(type = DATA, remark = "技能")
	public class SkillItem {
		public int id; // 道具id
		public int lvl;
		public int exp;
		public int nextExp;
	}

	@B2Class(type = DATA, remark = "阵形")
	public class FormationItem {
		public int id; // 道具id
		public int lvl;
		public int exp;
		public int nextExp;
	}

	@B2Class(type = DATA, remark = "舰位信息")
	public class MarineItem {
		public int id;
		public int pos;
		public int tonnage;
		public int num;
	}

	@B2Class(type = DATA, remark = "舰队信息信息")
	public class Marine {
		public int tonnage; // 吨位
		public int maxTonnage; // 总吨位
		public List<MarineItem> items; // 舰位信息
	}

	@B2Class(type = DATA, remark = "舰长信息")
	public class CaptainItem {
		public int id;
		public String mz;
		public int yhid;
		public String yhmz;
		public int jobId;
		public int type;
		public int lvl;
		public long exp;
		public long nextExp;
		public int icon; // 头像
		public Map<String, Integer> ability; // 能力名:能力值
		public Map<String, Integer> ability2; // 附加能力 能力名:能力值
		public String mainAttr; // 主属性
		public String auxAttr1; // 辅属性1
		public String auxAttr2; // 辅属性2
		public SkillItem mainSkill; // 主技能
		public SkillItem auxSkill; // 副技能
		public FormationItem formation; // 阵形
		public Map<Integer, Integer> equips; // 装备
		public Marine marine; // 舰队
		public List<Integer> gains; // 舰长增益
		public boolean autoFill;
		public boolean isDelete;

		public boolean isTraining; // 是否训练
		public int trainingGem; // 训练花费
		public String traningEndTm = ""; // 训练结束时间
		public int acqGemIncome; // 单次采集收入
		public long acqGemEndTm; // 采集冷却时间
		public int forceAcqGold; // 强制采集花费
		public int stat; // 舰长状态
		public int dockId; // 所在码头id
		public boolean isDef; // 默认舰长
		public int potential; // 潜力值
	}

	@B2Class(type = DATA, remark = "舰长经验奖励")
	public class CaptainExpReward {
		public int id;
		public String mz;
		public int exp;
	}

	@B2Class(type = DATA, remark = "海盗战斗奖励")
	public class PirateReward {
		public int silver; // 银币
		public int honor; // 荣誉
		public String props; // 道具
		public String equips; // 装备
		public List<CaptainExpReward> captainExps; // 舰长经验奖励
		public String autoReplenishment; // 自动补船
	}

	@B2Class(type = DATA, remark = "VIP信息")
	public class VipItem {
		public int id;
		public int gold; // 金币
		public String str; // 道具
	}

	@B2Class(type = DATA, remark = "VIPs信息")
	public class VipItems {
		public List<VipItem> items;
	}

	@B2Class(type = DATA, remark = "战斗舰位信息")
	public class XBerth {
		public int pos; // 位置
		public int jcid; // 舰船
		public int num; // 数量
		public double durable2; // 当前耐久
	}

	@B2Class(type = DATA, remark = "战斗舰长信息")
	public class XFleet {
		public int id;
		public String mz;
		public int lvl;
		public int icon; // 头像
		public int yhid;
		public String yhmz;
		public int ts; // 统帅
		public int ld; // 掠夺
		public int zc; // 侦查
		public int js; // 驾驶
		public int gj; // 攻击
		public int fy; // 防御
		public int x;
		public int y;

		public double tech_strong; // 船体加固学
		public double tech_gunpowder; // 火药学

		public SkillItem mainSkill; // 主技能
		public SkillItem auxSkill; // 辅技能
		public FormationItem formation; // 阵形

		public double attackGain; // 攻击增益
		public double defenseGain; // 防御增益
		public double skillGain; // 技能增益
		public double formationGain; // 阵形

		public List<XBerth> berths; // 舰船

		public int campId; // 阵营id
		public boolean isPirate; // 是否海盗
	}

	@B2Class(type = DATA, remark = "舰长心新属性")
	public class RenewAttr {
		public int ts;// "统帅"
		public int gj; // "攻击"
		public int fy; // "防御"
		public int ld; // "掠夺"
		public int js; // "驾驶"
		public int zc; // "侦查";
	}

	@B2Class(type = DATA, remark = "兑换金币价格")
	public class ExchangeGoldItem {
		public int id;
		public String mz;
		public int val;
	}

	@B2Class(type = DATA, remark = "兑换金币价格")
	public class ExchangeGoldItems {
		public List<ExchangeGoldItem> items;
	}

	@B2Class(type = DATA, remark = "战斗寻敌")
	public class FightPvpTargert {
		public int uid;
		public int icon;
		public String userMz;
		public int lvl;
		public String unionMz;
		public int knighthoodId;
		public String knighthood;
		public int strong;
		public int portId;
		public int dockId;
		public String dockMz;
		public long dockSilver;
		public int paySilver; // 寻敌花费银币
	}

	@B2Class(type = DATA, remark = "苹果商城购买金币价格")
	public class AppleItem {
		public int id;
		public String aid; // 苹果商品名字
		public double money; // 美元
		public int gold; // 兑换金币
		public int gift; // 赠送金币
	}

	@B2Class(type = DATA, remark = "用户基本信息")
	public class BaseUserInfo {
		public int uid;
		public int icon;
		public String userMz;
		public int lvl;
		public int ranking; // 排名
		public String unionMz; // 公会名字
		public String unionTitle; // 公会职位
		public int knighthoodId; // 爵位id
		public String knighthood;
	}

	// //////////////////////

	@B2Class(type = SERVER)
	interface OceanI {
		// //////////////////////////////////////////////////////////////////
		// 服务器端实现
		@B2Method(type = SERVER, params = { "s" }, remark = "ping")
		ReturnStatus ping(String s);

		@B2Method(type = SERVER, params = { "uuid", "sex", "outReged", "outMz" }, remark = "获取用户名")
		ReturnStatus getNewUserName(
				String uuid,
				int sex,
				@B2Param(oType = "OutBoolVal", isOut = true) OutBoolVal outReged,
				@B2Param(oType = "OutStrVal", isOut = true) OutStrVal outMz);

		@B2Method(type = SERVER, params = { "nick", "portId", "jobId",
				"headId", "uuid", "dvid", "token", "phone", "screen", "chnId",
				"ver", "outDat" }, remark = "注册")
		ReturnStatus register(String nick, int portId, int jobId, int headId,
				String uuid, String dvid, String token, String phone,
				String screen, String chnId, double ver,
				@B2Param(oType = "OutStringVal", isOut = true) OutStrVal outDat);

		@B2Method(type = SERVER, params = { "uuid", "dvid", "token", "phone",
				"screen", "ver", "outDat", "outHasPvp" }, remark = "登录")
		ReturnStatus login(
				String uuid,
				String dvid,
				String token,
				String phone,
				String screen,
				double ver,
				@B2Param(oType = "OutStringVal", isOut = true) OutStrVal outDat,
				@B2Param(oType = "OutBoolVal", isOut = true) OutBoolVal outHasPvp);

		@B2Method(type = SERVER, params = {}, remark = "心跳")
		ReturnStatus tick();

		@B2Method(type = SERVER, params = { "portId", "newMz" }, remark = "码头改名")
		ReturnStatus changeDockMz(int portId, String newMz);

		@B2Method(type = SERVER, params = { "portId", "opid" }, remark = "废弃码头,0:废弃1:取消")
		ReturnStatus abandonedPort(int portId, int opid);

		@B2Method(type = SERVER, params = {}, remark = "提升爵位")
		ReturnStatus advanceTitleOfNobility();

		@B2Method(type = SERVER, params = { "djid", "num" }, remark = "购买道具")
		ReturnStatus buyGood(int djid, int num);

		@B2Method(type = SERVER, params = { "ccid", "shipId" }, remark = "自动配置舰船")
		ReturnStatus autoAssemblyShip(int ccid, int shipId);

		@B2Method(type = SERVER, params = { "ccid", "pos", "shipId", "num" }, remark = "配置舰船")
		ReturnStatus assemblyShip(int ccid, int pos, int shipId, int num);

		@B2Method(type = SERVER, params = { "ccid" }, remark = "清空舰船")
		ReturnStatus clearAssemblyShip(int ccid);

		@B2Method(type = SERVER, params = { "dockId" }, remark = "集中银币")
		ReturnStatus transportSilver(int dockId);

		@B2Method(type = SERVER, params = { "captainId", "bSwitch" }, remark = "自动补充")
		ReturnStatus autoFillShip(int captainId, boolean bSwitch);

		@B2Method(type = SERVER, params = { "escapeRatio" }, remark = "逃跑设置")
		ReturnStatus setEscapeRatio(double escapeRatio);

		// 0:遗忘 1:设置主属性, 2:设置辅属性1 3:设置辅属性2
		@B2Method(type = SERVER, params = { "ccid", "attr", "opid" }, remark = "修改舰长属性")
		ReturnStatus changeCaptainAttribute(int ccid, String attr, int opid);

		@B2Method(type = SERVER, params = { "ccid", "force" }, remark = "采集珠宝")
		ReturnStatus collectionJewelry(int ccid, boolean force);

		@B2Method(type = SERVER, params = { "dockId" }, remark = "收藏码头")
		ReturnStatus collectDock(int dockId);

		@B2Method(type = SERVER, params = { "dockId", "toPortId", "useGold" }, remark = "港口迁移")
		ReturnStatus dockMove(int dockId, int toPortId, boolean useGold);

		@B2Method(type = SERVER, params = { "pid" }, remark = "丢弃道具")
		ReturnStatus dropProp(int pid);

		@B2Method(type = SERVER, params = { "str" }, remark = "反馈")
		ReturnStatus feedback(String str);

		@B2Method(type = SERVER, params = { "ccid" }, remark = "遗忘阵形")
		ReturnStatus forgetFormation(int ccid);

		@B2Method(type = SERVER, params = { "ccid", "skid" }, remark = "遗忘技能")
		ReturnStatus forgetSkill(int ccid, int skid);

		@B2Method(type = SERVER, params = { "step" }, remark = "新手步骤")
		ReturnStatus newbie(int step);

		@B2Method(type = SERVER, params = { "lineId", "type", "djid" }, remark = "0:金币 1:道具")
		ReturnStatus lineSpeed(int lineId, int type, int djid);

		@B2Method(type = SERVER, params = { "type" }, remark = "捐献 1:(67,68,69,71), 2:一个海盗旗 , 3:5个海盗旗")
		ReturnStatus payTribute(int type);

		@B2Method(type = SERVER, params = { "userTaskId" }, remark = "领取任务奖励")
		ReturnStatus rwReward(int userTaskId);

		@B2Method(type = SERVER, params = { "dockId", "page", "num" }, remark = "获取码头")
		ReturnStatus getDock(int dockId, int page, int num);

		@B2Method(type = SERVER, params = { "jcid", "num" }, remark = "强化舰船")
		ReturnStatus strengthenShip(int jcid, int num);

		@B2Method(type = SERVER, params = { "mz", "outUnions" }, remark = "查找公会")
		ReturnStatus unionSearch(String mz,
				@B2Param(oType = "Unions", isOut = true) Unions outUnions);

		@B2Method(type = SERVER, params = { "unionId" }, remark = "公会信息")
		ReturnStatus unionInfo(int unionId);

		@B2Method(type = SERVER, params = { "outUnion" }, remark = "进入公会")
		ReturnStatus unionEnter(
				@B2Param(oType = "UnionItem", isOut = true) UnionItem outUnion);

		@B2Method(type = SERVER, params = { "mz", "shortMz" }, remark = "创建公会")
		ReturnStatus unionCreate(String mz, String shortMz);

		@B2Method(type = SERVER, params = { "reqId" }, remark = "公会加入")
		ReturnStatus unionAgree(int reqId);

		@B2Method(type = SERVER, params = { "reqId" }, remark = "拒绝加入")
		ReturnStatus unionRefuse(int reqId);

		@B2Method(type = SERVER, params = {}, remark = "解散公会")
		ReturnStatus unionDissolution();

		@B2Method(type = SERVER, params = { "str" }, remark = "修改公会描述")
		ReturnStatus unionUpdate(String str);

		@B2Method(type = SERVER, params = { "mid" }, remark = "踢出成员")
		ReturnStatus unionTakeout(int mid);

		@B2Method(type = SERVER, params = { "unionId" }, remark = "公会请求")
		ReturnStatus unionRequest(int unionId);

		@B2Method(type = SERVER, params = { "title", "str" }, remark = "群发邮件")
		ReturnStatus unionSendMail(String title, String str);

		@B2Method(type = SERVER, params = { "jzid", "building" }, remark = "公会建筑升级")
		ReturnStatus unionUpLevel(
				int jzid,
				@B2Param(oType = "UnionBuilding", isOut = true) UnionBuilding outBuilding);

		@B2Method(type = SERVER, params = {}, remark = "退出公会")
		ReturnStatus unionLeave();

		@B2Method(type = SERVER, params = { "yhid", "jobId", "outMember" }, remark = "任命职位")
		ReturnStatus unionAppointed(
				int yhid,
				int jobId,
				@B2Param(oType = "UnionMember", isOut = true) UnionMember outMember);

		@B2Method(type = SERVER, params = { "gold", "outMember" }, remark = "公会捐献")
		ReturnStatus unionDonation(
				int gold,
				@B2Param(oType = "UnionMember", isOut = true) UnionMember outMember);

		@B2Method(type = SERVER, params = { "page", "size", "outMembers" }, remark = "公会募捐清单")
		ReturnStatus unionDonations(
				int page,
				int size,
				@B2Param(oType = "UnionMembers", isOut = true) UnionMembers outMembers);

		@B2Method(type = SERVER, params = { "page", "size", "outMembers" }, remark = "公会成员")
		ReturnStatus getUnionMembers(
				int page,
				int size,
				@B2Param(oType = "UnionMembers", isOut = true) UnionMembers outMembers);

		@B2Method(type = SERVER, params = { "page", "size", "outReqs" }, remark = "入会申请清单")
		ReturnStatus getUnionReqs(int page, int size,
				@B2Param(oType = "UnionReqs", isOut = true) UnionReqs outReqs);

		@B2Method(type = SERVER, params = { "outBuilding" }, remark = "公会建筑")
		ReturnStatus unionBuilding(
				@B2Param(oType = "UnionBuildings", isOut = true) UnionBuildings outBuilding);

		@B2Method(type = SERVER, params = { "ccid", "userDjid" }, remark = "使用道具")
		ReturnStatus useProp(int ccid, int userDjid);

		@B2Method(type = SERVER, params = { "receiverId", "receiverMz",
				"title", "content" }, remark = "发送邮件")
		ReturnStatus sendMail(int receiverId, String receiverMz, String title,
				String content);

		@B2Method(type = SERVER, params = { "type", "page", "size", "mails",
				"outType", "outUnReadNum", "outPageCount" }, remark = "邮件列表")
		ReturnStatus mailListing(
				int type,
				int page,
				int size,
				@B2Param(oType = "Mails", isOut = true) Mails mails,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outType,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outUnReadNum,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outPageCount);

		@B2Method(type = SERVER, params = { "mails" }, remark = "Pvp未读邮件列表")
		ReturnStatus pvpUnReadMails(
				@B2Param(oType = "Mails", isOut = true) Mails mails);

		@B2Method(type = SERVER, params = { "mailId", "outMail" }, remark = "阅读邮件")
		ReturnStatus getMail(int mailId,
				@B2Param(oType = "MailItem", isOut = true) MailItem outMail);

		@B2Method(type = SERVER, params = { "mailIds" }, remark = "删除邮件")
		ReturnStatus delMails(@B2Param(oType = "Integer") List<Integer> mailIds);

		@B2Method(type = SERVER, params = {}, remark = "增加探险次数")
		ReturnStatus upTimesExplore();

		@B2Method(type = SERVER, params = { "ccid", "opid" }, remark = "训练舰长")
		ReturnStatus trainingCaptain(int ccid, int opid);

		@B2Method(type = SERVER, params = { "dockId" }, remark = "扩容码头")
		ReturnStatus spaceExpansion(int dockId);

		@B2Method(type = SERVER, params = { "userEquipId" }, remark = "卖出装备")
		ReturnStatus sellEquip(int userEquipId);

		@B2Method(type = SERVER, params = { "white", "green", "blue", "violet" }, remark = "一键卖出装备, 品质,0:白色,1:绿色,2:蓝色,3紫色")
		ReturnStatus aKeySellEquip(boolean white, boolean green, boolean blue,
				boolean violet);

		@B2Method(type = SERVER, params = { "captainId", "isWear" }, remark = "一键装备. isWearc 换上/卸下")
		ReturnStatus aKeyWear(int captainId, boolean isWear);

		@B2Method(type = SERVER, params = { "ccid", "outAttr" }, remark = "舰长重修")
		ReturnStatus renewCaptainAttr(int ccid,
				@B2Param(oType = "RenewAttr", isOut = true) RenewAttr outAttr);

		@B2Method(type = SERVER, params = { "ccid", "useGold" }, remark = "保存舰长重修结果")
		ReturnStatus comfirmRenewCaptainAttr(int ccid, boolean useGold);

		@B2Method(type = SERVER, params = { "ccid", "newmz" }, remark = "舰长改名")
		ReturnStatus renameCaptain(int ccid, String newmz);

		@B2Method(type = SERVER, params = { "userEquipId", "useGold" }, remark = "装备重炼")
		ReturnStatus refineEquipage(int useEquipId, boolean useGold);

		@B2Method(type = SERVER, params = { "actId" }, remark = "活动礼包")
		ReturnStatus receiveGift(int actId);

		@B2Method(type = SERVER, params = {}, remark = "VIP礼包")
		ReturnStatus receiveVIPGift();

		@B2Method(type = SERVER, params = { "dockId" }, remark = "查找码头")
		ReturnStatus findDock(int dockId);

		@B2Method(type = SERVER, params = { "isSandbox", "pid", "aid",
				"receipt", "num" }, remark = "app store充值")
		ReturnStatus paymentCharge(boolean isSandbox, String pid, String aid,
				String receipt, int num);

		@B2Method(type = SERVER, params = { "routeId" }, remark = "航线返航")
		ReturnStatus marineHomeward(int routeId);

		@B2Method(type = SERVER, params = { "clear", "fids" }, remark = "删除收藏夹")
		ReturnStatus delFavorite(boolean clear,
				@B2Param(oType = "Integer") List<Integer> fids);

		@B2Method(type = SERVER, params = { "ccid" }, remark = "采集冷却")
		ReturnStatus cooldownColleaction(int ccid);

		@B2Method(type = SERVER, params = { "ccid" }, remark = "解雇船长")
		ReturnStatus fireCaptain(int ccid);

		@B2Method(type = SERVER, params = { "type", "page", "size" }, remark = "排行榜 // 0:爵位 1:舰长 2:财富 3:积分 4:公会")
		void getBillboard(int type, int page, int size);

		@B2Method(type = SERVER, params = { "gkid" }, remark = "殖民检查")
		void checkColony(int gkid);

		@B2Method(type = SERVER, params = { "gkid", "useGold" }, remark = "殖民")
		ReturnStatus colony(int gkid, boolean useGold);

		@B2Method(type = SERVER, params = {}, remark = "当前港口对应海盗清单")
		ReturnStatus getPiratesByCurrentPortId();

		@B2Method(type = SERVER, params = { "portId" }, remark = "海盗对应码头清单")
		ReturnStatus getPirateDocksByPortId(int portId);

		@B2Method(type = SERVER, params = { "ccid" }, remark = "取得舰长信息")
		ReturnStatus getCaptainById(int ccid);

		@B2Method(type = SERVER, params = { "pirateDockId", "memberNum", "type" }, remark = "组队剿海盗")
		ReturnStatus beginPirateGroup(int pirateDockId, int memberNum, int type);

		@B2Method(type = SERVER, params = { "groupId", "outTime" }, remark = "取消组队剿海盗")
		ReturnStatus endPirateGroup(int groupId,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outTime);

		@B2Method(type = SERVER, params = { "dockId" }, remark = "购买护盾")
		ReturnStatus buyShield(int dockId);

		@B2Method(type = SERVER, params = {}, remark = "退出游戏")
		void quit();

		@B2Method(type = SERVER, params = { "gid", "rmb" }, remark = "25PP创建充值订单")
		ReturnStatus createOrder25PP(long gid, double rmb);

		@B2Method(type = SERVER, params = { "isOK", "page", "size" }, remark = "取得25PP订单")
		void getOrders(boolean isOK, int page, int size);

		@B2Method(type = SERVER, params = { "gid", "rmb" }, remark = "91创建充值订单")
		ReturnStatus createOrder91(long gid, double rmb);

		@B2Method(type = SERVER, params = { "gid", "rmb" }, remark = "小米创建充值订单")
		ReturnStatus createOrderMi(long gid, double rmb);

		@B2Method(type = SERVER, params = { "chnMz", "gid", "rmb" }, remark = "[UC/D乐/移动]创建充值订单")
		ReturnStatus createOrder(String chnMz, long gid, double rmb);

		@B2Method(type = SERVER, params = {}, remark = "获得港口的状态")
		ReturnStatus getPortsStat();

		@B2Method(type = SERVER, params = {}, remark = "用户的装备")
		ReturnStatus getUserEquips();

		@B2Method(type = SERVER, params = {}, remark = "用户的道具")
		ReturnStatus getUserProps();

		@B2Method(type = SERVER, params = { "portId2", "captainIds",
				"outSilver", "outTm" }, remark = "计算出征消耗")
		ReturnStatus marineDepartCalculate(
				int portId2,
				@B2Param(oType = "Integer") List<Integer> captainIds,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outSilver,
				@B2Param(oType = "OutLongVal", isOut = true) OutLongVal outTm);

		@B2Method(type = SERVER, params = { "dockId2", "cmd", "captainIds",
				"silver" }, remark = "出征")
		ReturnStatus marineDepart(int dockId2, int cmd,
				@B2Param(oType = "Integer") List<Integer> captainIds, int silver);

		@B2Method(type = SERVER, params = { "type", "force", "useGold",
				"outType", "outGem" }, remark = "挖宝石(绿宝石,蓝宝石,紫宝石)")
		ReturnStatus excavateGem(int type, boolean force, boolean useGold,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outType,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outGem);

		@B2Method(type = SERVER, params = { "yhid2", "mz2", "msg" }, remark = "私聊")
		ReturnStatus privateChat(int yhid2, String mz2, String msg);

		@B2Method(type = SERVER, params = { "msg" }, remark = "公聊")
		ReturnStatus publicChat(String msg);

		@B2Method(type = SERVER, params = { "groupId", "msg" }, remark = "邀请组队")
		ReturnStatus inviteGroup(int groupId, String msg);

		@B2Method(type = SERVER, params = { "groupId" }, remark = "加入组队")
		ReturnStatus joinInviteGroup(int groupId);

		@B2Method(type = SERVER, params = { "msg" }, remark = "公会聊天")
		ReturnStatus unionChat(String msg);

		@B2Method(type = SERVER, params = { "msg" }, remark = "喇叭")
		ReturnStatus laba(String msg);

		@B2Method(type = SERVER, params = { "zbid", "fw", "useGold", "outRate",
				"outMainRate", "outGold", "outForceGold" }, remark = "强化参数")
		ReturnStatus getStrengthenEquipage(
				int zbid,
				int fw,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outZbid,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outRate,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outMainRate,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outGem,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outForceGold);

		@B2Method(type = SERVER, params = { "zbid", "fw", "useGold",
				"outSuccess" }, remark = "强化装备")
		ReturnStatus strengthenEquipage(
				int zbid,
				int fw,
				boolean useGold,
				@B2Param(oType = "OutBoolVal", isOut = true) OutBoolVal outSuccess);

		@B2Method(type = SERVER, params = { "useGold", "djid", "outTm" }, remark = "冷却建筑队列")
		ReturnStatus cooldownBuilding(boolean useGold, int djid,
				@B2Param(oType = "OutLongVal", isOut = true) OutLongVal outTm);

		@B2Method(type = SERVER, params = { "useGold", "djid", "outTm" }, remark = "冷却科技队列")
		ReturnStatus cooldownTech(boolean useGold, int djid,
				@B2Param(oType = "OutLongVal", isOut = true) OutLongVal outTm);

		@B2Method(type = SERVER, params = { "useGold", "djid", "outTm" }, remark = "冷却生产队列")
		ReturnStatus cooldownFacility(boolean useGold, int djid,
				@B2Param(oType = "OutLongVal", isOut = true) OutLongVal outTm);

		@B2Method(type = SERVER, params = { "buidingId", "useGold", "outTm",
				"outPredisp" }, remark = "建筑升降级")
		ReturnStatus upOrDownBuilding(
				int buidingId,
				boolean useGold,
				@B2Param(oType = "OutLongVal", isOut = true) OutLongVal outTm,
				@B2Param(oType = "OutStrListVal", isOut = true) OutStrListVal outPredisp);

		@B2Method(type = SERVER, params = { "teachId", "useGold", "outTm",
				"outPredisp" }, remark = "科技升级")
		ReturnStatus upTech(
				int teachId,
				boolean useGold,
				@B2Param(oType = "OutLongVal", isOut = true) OutLongVal outTm,
				@B2Param(oType = "OutStrListVal", isOut = true) OutStrListVal outPredisp);

		@B2Method(type = SERVER, params = { "productId", "num", "useGold",
				"outTm", "outPredisp" }, remark = "生产")
		ReturnStatus productFacility(
				int productId,
				int num,
				boolean useGold,
				@B2Param(oType = "OutLongVal", isOut = true) OutLongVal outTm,
				@B2Param(oType = "OutStrListVal", isOut = true) OutStrListVal outPredisp);

		@B2Method(type = SERVER, params = { "captainId", "equipId", "isWear" }, remark = "穿戴装备")
		ReturnStatus wearEquip(int captainId, int equipId, boolean isWear);

		@B2Method(type = SERVER, params = { "captainType", "outCaptainId" }, remark = "招募舰长")
		ReturnStatus recruitCaptain(
				int captainType,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outCaptainId);

		@B2Method(type = SERVER, params = { "captainId", "type", "outEx",
				"outExid" }, remark = "创建探险")
		ReturnStatus createExplore(int captainId, int type,
				@B2Param(oType = "IdMzItems", isOut = true) IdMzItems outEx,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outExid);

		@B2Method(type = SERVER, params = { "exId", "txdid", "outEx", "outGem" }, remark = "执行探险")
		ReturnStatus startExplore(int exId, int txdid,
				@B2Param(oType = "IdMzItems", isOut = true) IdMzItems outEx,
				@B2Param(oType = "OutIntVal", isOut = true) OutIntVal outGem);

		@B2Method(type = SERVER, params = { "exId", "outEx" }, remark = "刷新探险地点")
		ReturnStatus nextExploreIslands(int exId,
				@B2Param(oType = "IdMzItems", isOut = true) IdMzItems outEx);

		@B2Method(type = SERVER, params = {}, remark = "购买道具背包")
		ReturnStatus expPropsPack();

		@B2Method(type = SERVER, params = {}, remark = "购买装备背包")
		ReturnStatus expEquipPack();

		@B2Method(type = SERVER, params = {}, remark = "购买训练位")
		ReturnStatus expTrainSeat();

		@B2Method(type = SERVER, params = { "gridId", "captainId" }, remark = "舰长九宫格, 布阵")
		ReturnStatus captainGrid(int gridId, int captainId);

		@B2Method(type = SERVER, params = { "gridId" }, remark = "购买九宫格, 布阵")
		ReturnStatus buyGrid(int gridId);

		@B2Method(type = SERVER, params = { "outDiscount", "outHots",
				"outCaptains", "outBoxs", "outOthers" }, remark = "商品")
		ReturnStatus markets(
				@B2Param(oType = "OutDoubleVal", isOut = true) OutDoubleVal outDiscount,
				@B2Param(oType = "MarketItems", isOut = true) MarketItems outHots,
				@B2Param(oType = "MarketItems", isOut = true) MarketItems outCaptains,
				@B2Param(oType = "MarketItems", isOut = true) MarketItems outBoxs,
				@B2Param(oType = "MarketItems", isOut = true) MarketItems outOthers);

		@B2Method(type = SERVER, params = {}, remark = "获得收藏夹")
		ReturnStatus getFavorites();

		@B2Method(type = SERVER, params = { "num" }, remark = "购买私掠许可证")
		ReturnStatus buyPrivateerLic(int num);

		@B2Method(type = SERVER, params = { "dockId", "num", "outReward" }, remark = "自动刷海盗")
		ReturnStatus autoRefPirate(
				int dockId,
				int num,
				@B2Param(oType = "PirateReward", isOut = true) PirateReward outReward);

		@B2Method(type = SERVER, params = { "dockId" }, remark = "切换当前码头")
		ReturnStatus changeCurrentDock(int dockId);

		@B2Method(type = SERVER, params = { "dockId", "buildId" }, remark = "建筑信息")
		ReturnStatus getBuildingInfo(int dockId, int buildId);

		@B2Method(type = SERVER, params = { "minVip", "outVips" }, remark = "vip信息")
		ReturnStatus vipInfo(int minVip,
				@B2Param(oType = "VipItems", isOut = true) VipItems outVips);

		@B2Method(type = SERVER, params = { "outTargert" }, remark = "寻敌Pvp战斗")
		ReturnStatus findFightPvpTargert(
				@B2Param(oType = "FightPvpTargert", isOut = true) FightPvpTargert outTargert);

		@B2Method(type = SERVER, params = { "mtid2" }, remark = "开始战斗(战斗主攻)")
		ReturnStatus startBattle(int mtid2);

		@B2Method(type = SERVER, params = { "ccid", "ccid2", "x1", "y1", "x2",
				"y2" }, remark = "寻敌")
		ReturnStatus battleTargert(int ccid, int ccid2, int x1, int y1, int x2,
				int y2);

		@B2Method(type = SERVER, params = { "ccid", "ccid2", "x1", "y1", "x2",
				"y2", "jnid1", "jnid2", "zxid", "fjnid2", "berths" }, remark = "攻击(船长1,船长2,主技能,辅技能,阵形,被攻方技能)")
		ReturnStatus battleAttack(int ccid, int ccid2, int x1, int y1, int x2,
				int y2, int jnid1, int jnid2, int zxid, int fjnid2,
				@B2Param(oType = "XBerth") List<XBerth> berths);

		@B2Method(type = SERVER, params = {}, remark = "结束战斗")
		ReturnStatus endBattle();

		@B2Method(type = SERVER, params = { "isWatch" }, remark = "观看战斗")
		ReturnStatus watchBattle(boolean isWatch);

		@B2Method(type = SERVER, params = { "mtid", "outStr" }, remark = "侦查码头")
		ReturnStatus investigate(int mtid,
				@B2Param(oType = "OutStrVal", isOut = true) OutStrVal outStr);

		@B2Method(type = SERVER, params = { "captainId", "jobId", "useGold" }, remark = "转职")
		ReturnStatus changeOccupation(int captainId, int jobId, boolean useGold);

		@B2Method(type = SERVER, params = { "deviceToken" }, remark = "变更deivceToken")
		ReturnStatus changeDeviceToken(String deviceToken);

		@B2Method(type = SERVER, params = { "uid", "outUser" }, remark = "获取指定用户基本信息")
		ReturnStatus getBaseUserInfo(
				int uid,
				@B2Param(oType = "BaseUserInfo", isOut = true) BaseUserInfo outUser);

		@B2Method(type = SERVER, params = { "uc_sid", "isDebug", "outUcid" }, remark = "UC渠道，通过sessid获取ucid")
		ReturnStatus getUcid(String uc_sid, boolean isDebug,
				@B2Param(oType = "OutStrVal", isOut = true) OutStrVal outUcid);

		@B2Method(type = SERVER, params = {}, remark = "删号")
		ReturnStatus deleteMySelf();

		@B2Method(type = SERVER, params = {"buff", "outBuff"}, remark = "echo")
		ReturnStatus echo(OutStrVal buff, @B2Param(oType = "OutBytesVal", isOut = true) OutBytesVal outBuff);

		// //////////////////////////////////////////////////////////////////
		// 客户器端实现
		@B2Method(type = CLIENT, params = { "s" }, remark = "pong")
		void pong(String s);

		@B2Method(type = CLIENT, params = { "dockId", "silver", "mailNum",
				"hasPvp" }, remark = "资源情况")
		void pushResource(int dockId, int silver, int mailNum, boolean hasPvp);

		@B2Method(type = CLIENT, params = { "page", "count", "docks" }, remark = "发送码头")
		void pushDock(int page, int count,
				@B2Param(oType = "Dock") List<Dock> docks);

		@B2Method(type = CLIENT, params = { "tips" }, remark = "跳动提示")
		void pushTips(@B2Param(oType = "TipItem") List<TipItem> tips);

		@B2Method(type = CLIENT, params = { "type", "bills" }, remark = "排行榜")
		void pushBillboard(int type,
				@B2Param(oType = "BillItem") List<BillItem> bills);

		@B2Method(type = CLIENT, params = { "pirateStatus" }, remark = "开放的海盗港口")
		void pushPortPirate(
				@B2Param(oType = "Integer") List<Integer> pirateStatus);

		@B2Method(type = CLIENT, params = { "silver", "succ", "msg" }, remark = "殖民条件")
		void pushCheckColony(int silver, int succ, String msg);

		@B2Method(type = CLIENT, params = { "dockId", "targertPortId", "opens",
				"closed" }, remark = "港口开放的海盗清单")
		void pushPiratesByPortId(int dockId, int targertPortId,
				@B2Param(oType = "Integer") List<Integer> opens,
				@B2Param(oType = "Integer") List<Integer> closed);

		@B2Method(type = CLIENT, params = { "portId", "docks" }, remark = "海盗对应码头清单")
		void pushPirateDocksByPortId(int portId,
				@B2Param(oType = "PirateDockItem") List<PirateDockItem> docks);

		@B2Method(type = CLIENT, params = { "groupId", "stat" }, remark = "海盗剿灭队伍状态, stat:0:开队伍,1:出征,2:关闭")
		void pushPirateGroupState(int groupId, int stat);

		@B2Method(type = CLIENT, params = { "groupId", "yhid", "txid", "mz",
				"stat" }, remark = "舰长加入/退出剿灭海盗队伍, stat:true开始,false退出")
		void pushPirateGroupCaptionStat(int groupId, int yhid, int txid,
				String mz, boolean stat);

		@B2Method(type = CLIENT, params = { "succ", "msg", "orderId", "gold" }, remark = "创建订单成功")
		void pushCreateOrder(int succ, String msg, int orderId, int gold);

		@B2Method(type = CLIENT, params = { "succ", "msg", "isOK", "orders",
				"page", "pageCount" }, remark = "取得订单, type:0 未到账, 1:所有")
		void pushOrders(int succ, String msg, boolean isOK,
				@B2Param(oType = "OrderItem25PP") List<OrderItem25PP> orders,
				int page, int pageCount);

		@B2Method(type = CLIENT, params = { "stats" }, remark = "港口的开放状态")
		void pushPortsStat(
				@B2Param(oType = "PortStatItem") List<PortStatItem> stats);

		@B2Method(type = CLIENT, params = { "equips" }, remark = "用户的装备清单")
		void pushUserEquips(@B2Param(oType = "EquipItem") List<EquipItem> equips);

		@B2Method(type = CLIENT, params = { "props" }, remark = "用户的道具清单")
		void pushUserProps(@B2Param(oType = "PropItem") List<PropItem> equips);

		@B2Method(type = CLIENT, params = { "msg" }, remark = "聊天信息")
		void pushChat(MsgItem msg);

		@B2Method(type = CLIENT, params = { "union" }, remark = "公会信息")
		void pushUnionInfo(UnionItem union);

		@B2Method(type = CLIENT, params = { "voyage" }, remark = "航线信息")
		void pushVoyage(VoyageItem voyage);

		@B2Method(type = CLIENT, params = { "favorites" }, remark = "获得收藏夹")
		void pushFavorites(
				@B2Param(oType = "FavoriteItem") List<FavoriteItem> favorites);

		@B2Method(type = CLIENT, params = { "captain" }, remark = "舰长信息")
		void pushCaptain(CaptainItem captain);

		@B2Method(type = CLIENT, params = { "isBoss", "fleets" }, remark = "战斗开始舰长")
		void pushBattle(boolean isBoss,
				@B2Param(oType = "XFleet") List<XFleet> fleets);

		@B2Method(type = CLIENT, params = { "ccid", "ccid2" }, remark = "寻敌")
		ReturnStatus pushBattleTargert(int ccid, int ccid2);

		@B2Method(type = CLIENT, params = { "ccid", "ccid2", "jnid1", "jnid2",
				"zxid", "fjnid2", "berths" }, remark = "攻击(船长1,船长2,主技能,辅技能,阵形,被攻方技能)")
		ReturnStatus pushBattleAttack(int ccid, int ccid2, int jnid1,
				int jnid2, int zxid, int fjnid2,
				@B2Param(oType = "XBerth") List<XBerth> berths);

		@B2Method(type = CLIENT, params = { "achievement", "ourLoss",
				"enmeyLoss", "str", "reward" }, remark = "战斗结束奖励,-5完败，-4溃败,-3失败,-2小败,-1:惜败 0:战斗结束 1:战斗胜利 (1:险胜,2:胜利,3:小胜,4:大胜,5:完胜)")
		ReturnStatus pushEndBattle(int achievement, int ourLoss, int enmeyLoss,
				String str, PirateReward reward);

		@B2Method(type = CLIENT, params = { "upgradeUrl", "scoreUrl" }, remark = "外部访问Url地址")
		void pushOutUrls(String upgradeUrl, String scoreUrl);

		@B2Method(type = CLIENT, params = { "items" }, remark = "金币兑换价格")
		void pushExchangeGoldItems(
				@B2Param(oType = "ExchangeGoldItem") List<ExchangeGoldItem> items);

		@B2Method(type = CLIENT, params = { "isPvp" }, remark = "是否有pvp信息")
		void pushIsPvp(boolean isPvp);

		@B2Method(type = CLIENT, params = { "items" }, remark = "苹果商品价格")
		void pushAppleItems(@B2Param(oType = "AppleItem") List<AppleItem> items);

		@B2Method(type = CLIENT, params = { "notices" }, remark = "公告")
		ReturnStatus pushNotices(
				@B2Param(oType = "OutStrVal") OutStrVal outNotices);

		@B2Method(type = CLIENT, params = { "outHdid", "outPortId", "outMtid",
				"outMemberNum", "outGroupId", "outMsg" }, remark = "邀请组队下行")
		ReturnStatus pushInviteGroup(OutIntVal outHdid, OutIntVal outPortid,
				OutIntVal outMtid, OutIntVal outMemberNum,
				OutIntVal outGroupId, MsgItem outMsg);
	}

	public static void main(String[] args) throws Exception {
		Class<?> c = OceanG.class;
		boolean src = FileEx.exists("src");
		Bio2GJava.b2g(c, src);
		Bio2GCSharp.b2g(c, src);
	}

}
