Class(
		'App',
		'linb.Com',
		{
			Instance : {
				autoDestroy : true,

				iniComponents : function() {
					// [[code created by jsLinb UI Builder
					var host = this, children = [], append = function(child) {
						children.push(child.get(0))
					};

					append((new linb.UI.Pane)
							.host(host, "divHolder")
							.setLeft(70)
							.setTop(40)
							.setWidth(878)
							.setHeight(508)
							.setTabindex(0)
							.setCustomStyle(
									{
										"KEY" : "background:#ffffff;border:solid 1px #888"
									}));

					host.divHolder
							.append((new linb.UI.Div)
									.host(host, "div72")
									.setDock("top")
									.setHeight(28)
									.setTabindex(0)
									.setCustomStyle(
											{
												"KEY" : "background:#000000;border:solid 1px #888"
											}));

					host.divHolder
							.append((new linb.UI.Div)
									.host(host, "div73")
									.setDock("top")
									.setHeight(68)
									.setTabindex(0)
									.setCustomStyle(
											{
												"KEY" : "background:#b1fe94;border:solid 1px #888"
											}));

					host.divHolder.append((new linb.UI.Image).host(host,
							"imgTelephone").setLeft(140).setTop(440).setWidth(
							48).setHeight(38).setTabindex(0).setSrc(
							"img/telephone.gif").setCustomStyle( {
						"KEY" : "background:#b1fe94;border:solid 1px #888"
					}));

					host.divHolder
							.append((new linb.UI.Label)
									.host(host, "lblTelephone")
									.setLeft(200)
									.setTop(440)
									.setWidth(230)
									.setTabindex(0)
									.setCaption(
											"お電話によるお問合せはこちらから<br />月曜日～金曜日（祝祭日除く）　●●時～●●時<br />81-03-5822-1566")
									.setFontSize("12px"));

					host.divHolder
							.append((new linb.UI.Image)
									.host(host, "imgEmail")
									.setLeft(480)
									.setTop(440)
									.setWidth(48)
									.setHeight(38)
									.setTabindex(0)
									.setSrc("img/email.gif")
									.setCustomStyle(
											{
												"KEY" : "background:#b1fe94;border:solid 1px #888"
											}));

					host.divHolder
							.append((new linb.UI.Label)
									.host(host, "lblEmail")
									.setLeft(530)
									.setTop(440)
									.setWidth(270)
									.setHeight(50)
									.setTabindex(0)
									.setCaption(
											"メールによるお問合せはこちらから                                                                                          <br />                                             ****@***********<br />                                              土日祝祭日を除く●営業日以内にご返答致します。"));

					host.divHolder.append((new linb.UI.Div).host(host,
							"divError").setLeft(200).setTop(110).setWidth(290)
							.setHeight(50).setTabindex(0));

					host.divHolder.append((new linb.UI.Image).host(host,
							"imageFRC").setTop(5).setWidth(107).setHeight(20)
							.setRight(5).setSrc("img/frclogo2.gif").onClick(
									"_imgfrc_onclick"));

					host.divHolder
							.append((new linb.UI.Image)
									.host(host, "imgToumei")
									.setLeft(20)
									.setTop(130)
									.setWidth(90)
									.setHeight(48)
									.setSrc("img/logo_honto_toumei_170.gif")
									.onClick("_imgtoumei_onclick")
									.setCustomStyle(
											{
												"KEY" : "background:#b1fe94;border:solid 1px #888"
											}));

					host.divHolder
							.append((new linb.UI.Image)
									.host(host, "imgYaonet")
									.setLeft(20)
									.setTop(270)
									.setWidth(90)
									.setHeight(48)
									.setSrc("img/yaonet.gif")
									.onClick("_imgyaonet_onclick")
									.setCustomStyle(
											{
												"KEY" : "background:#b1fe94;border:solid 1px #888"
											}));


					host.divHolder.append((new linb.UI.Label).host(host,
							"lblUsername").setLeft(140).setTop(170).setCaption(
							"$app.paste"));

					host.divHolder.append((new linb.UI.Input).host(host,
							"iptUsername").setLeft(270).setTop(168).setWidth(
							220).setHeight(23).setTipsErr(
							"$app.invaliduserpassword").setValueFormat("[^.*]")
					// .setTipsBinder("divError")
							);

					host.divHolder.append((new linb.UI.Label).host(host,
							"lblWelcome").setLeft(290).setTop(50).setWidth(280)
							.setHeight(40).setCaption("WELCOME TO EON")
							.setHAlign("left").setFontSize("28px")
							.setFontWeight("BOLD"));

					host.divHolder.append((new linb.UI.Label).host(host,
							"lblPassword").setLeft(140).setTop(202).setCaption(
							"Password"));

					host.divHolder
							.append((new linb.UI.Image)
									.host(host, "image23")
									.setLeft(270)
									.setTop(280)
									.setWidth(10)
									.setHeight(10)
									.setSrc("img/list.gif")
									.setCustomStyle(
											{
												"KEY" : "background:#b1fe94;border:solid 1px #888"
											}));

					host.divHolder
							.append((new linb.UI.Image)
									.host(host, "image24")
									.setLeft(270)
									.setTop(310)
									.setWidth(10)
									.setHeight(10)
									.setSrc("img/list.gif")
									.setCustomStyle(
											{
												"KEY" : "background:#b1fe94;border:solid 1px #888"
											}));

					host.divHolder
							.append((new linb.UI.Image)
									.host(host, "image25")
									.setLeft(270)
									.setTop(340)
									.setWidth(10)
									.setHeight(10)
									.setSrc("img/list.gif")
									.setCustomStyle(
											{
												"KEY" : "background:#b1fe94;border:solid 1px #888"
											}));

					host.divHolder.append((new linb.UI.Input).host(host,
							"iptPassword").setLeft(270).setTop(200).setWidth(
							220).setHeight(23).setTabindex(2).setTipsErr(
							"$app.invaliduserpassword").setType("password")
							.setValueFormat("[^.*]")
					// .setTipsBinder("divError")
							);

					host.divHolder.append((new linb.UI.Button).host(host,
							"btnLogin").setLeft(270).setTop(240).setWidth(80)
							.setTabindex(3).setCaption("Login").onClick(
									"_btnlogin_onclick"));

					host.divHolder.append((new linb.UI.CheckBox).host(host,
							"chkSaveIdPassword").setLeft(350).setTop(240)
							.setWidth(150).setTabindex(4).setCaption(
									"Save ID & Password").onChecked(
									"_checkbox1_onchecked"));

					host.divHolder.append((new linb.UI.Link).host(host,
							"lnkForgotPassword").setLeft(290).setTop(280)
							.setWidth(140).setTabindex(5).setCaption(
									"Forgot Password").onClick(
									"_lnkforgotpassword_onclick"));

					host.divHolder.append((new linb.UI.Link).host(host,
							"lnkLoginHelp").setLeft(290).setTop(310).setWidth(
							140).setTabindex(6).setCaption("Login Help")
							.onClick("_lnkloginhelp_onclick"));

					host.divHolder.append((new linb.UI.Link).host(host,
							"lnkNonMembers").setLeft(290).setTop(340).setWidth(
							140).setTabindex(7).setCaption("To Non-members")
							.onClick("_lnknonmembers_onclick"));

					host.divHolder.append((new linb.UI.Link).host(host,
							"lnkYaonet").setLeft(20).setTop(270).setWidth(92)
							.setHeight(50).setTabindex(11).setCaption("")
							.onClick("_imgyaonet_onclick"));

					host.divHolder
							.append((new linb.UI.Div)
									.host(host, "divAnnouncement")
									.setLeft(550)
									.setTop(130)
									.setWidth(310)
									.setHeight(270)
									.setTabindex(13)
									.setCustomStyle(
											{
												"KEY" : "background:#ffffff;border:solid 1px #888"
											}));

					append((new linb.UI.Link).host(host, "lnkFRC").setLeft(830)
							.setTop(40).setWidth(120).setHeight(31)
							.setRight(10).setTabindex(8).setCaption("")
							.onClick("_lnkFRC_onclick"));

					append((new linb.UI.Link).host(host, "lnkToumei").setLeft(
							91).setTop(171).setWidth(92).setHeight(50)
							.setTabindex(9).setCaption("").onClick(
									"_imgtoumei_onclick"));




					return children;
					// ]]code created by jsLinb UI Builder
				},
				events : {
					"onReady" : "_onready",
					"onRender" : "_onRender"
				},
				customAppend : function(parent, subId, left, top) {
					return false;
				},
				iniResource : function(com, threadid) {
				},
				iniExComs : function(com, hreadid) {
				},
				_onready : function() {
					this.divAnnouncement
							.setHtml("<iframe frameborder=0 marginheight=0 marginwidth=0 scrolling=0 width=100% height=100% src='https://meta2.freshremix.com/eon/eon_message_general.html' </iframe>");
				},
				_imgfrc_onclick : function(profile, e, value) {
					window
							.open('https://meta2.freshremix.com/seika_info/index.html');
				},
				_imgtoumei_onclick : function(profile, e, value) {
					window.open('http://www.hontoichiba.com');
				},
				_imgyaonet_onclick : function(profile, e, value) {
					window
							.open('http://store.shopping.yahoo.co.jp/yaonet/index.html');
				},
				_lnkloginhelp_onclick : function(profile, e) {
					var browserName = navigator.appName;
					if (browserName == "Microsoft Internet Explorer") {
						window.showModalDialog("loginHelp.html", 'Login Help',
								'dialogWidth:15;dialogHeight:10;');
					} else {
						mywindow = window
								.open("loginHelp.html", "Login Help",
										"location=0,status=0,scrollbars=0,menubar=0,resizable=0,width=340,height=130");
						mywindow.moveTo(250, 300);
					}
				},
				_lnkforgotpassword_onclick : function(profile, e) {
					var browserName = navigator.appName;
					if (browserName == "Microsoft Internet Explorer") {
						window.showModalDialog("ForgotPassword.html", 'Window',
								'dialogWidth:25;dialogHeight:15;');
					} else {
						mywindow = window
								.open("ForgotPassword.html", "Forgot Password",
										"location=0,status=0,scrollbars=0,menubar=0,resizable=0,width=440,height=230");
						mywindow.moveTo(250, 300);
					}
				},
				_lnknonmembers_onclick : function(profile, e) {
					var browserName = navigator.appName;
					if (browserName == "Microsoft Internet Explorer") {
						window.showModalDialog("inquiry.html", 'Login Help',
								'dialogWidth:50;dialogHeight:34;');
					} else {
						mywindow = window
								.open("inquiry.html", "Login Help",
										"location=0,status=0,scrollbars=0,menubar=0,resizable=0,width=840,height=530");
						mywindow.moveTo(50, 50);
					}

				},
				_btnlogin_onclick : function(profile, e, src, value) {
					var ns = this;
//					var url = "/eON/login.json";
					var url = "/eON/j_spring_security_check";
					var obj = new Object();
					obj.userName = this.iptUsername.getUIValue();
					obj.password = this.iptPassword.getUIValue();
					if ((obj.userName == "") || (obj.password == "")) {
						// alert("ユーザ名とパスワードを入力してください。");
				this.divError.setUICaption("User or Password is invalid.");
				return false;
			}
			linb.Ajax(url, obj
//					, function(response) {
//						var obj = _.unserialize(response);
//						// alert(response);
//							if (obj.fail) {
//								var field = new Object();
//								field = obj.field;
//								divError.setCaption(field.userName);
//							} else if (obj.message != null) {
//								var field = new Object();
//								field = obj.field;
//								linb.alert(obj.message);
//							} else {
//								// TODO: redirection to page as per role
//							var user = new Object();
//							user = obj.response;
//							var role = new Object();
//							role = user.role;
//							// alert(role.roleName);
//							if (role.adminFlag == "1")
//								window.location.href = "/eON/app/eon/admin/index.jsp";
//							if (role.sellerFlag == "1"
//									|| role.sellerAdminFlag == "1")
//								window.location.href = "/eON/app/eon/seller/index.html";
//							if (role.buyerFlag == "1"
//									|| role.buyerAdminFlag == "1")
//								window.location.href = "/eON/app/eon/buyer/index.html";
//						}
//					}, function(msg, type, id) {
//						alert(msg);
//						linb.message("失敗： " + msg);
//					}, null, {
//						method : 'POST'
//					}
					).start();

		},
		_checkbox1_onchecked : function(profile, e, value) {
			alert(e);
		},
		_lnkFRC_onclick : function(profile, e) {
			window.open('https://meta2.freshremix.com/seika_info/index.html');
		}
			}
		});