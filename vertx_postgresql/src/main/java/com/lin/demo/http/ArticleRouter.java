package com.lin.demo.http;

import java.util.UUID;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
/**
 * 调试方法：
 * http://localhost:8080/article/add
 * http://localhost:8080/article/findAll
 * http://localhost:8080/article/update?fld_id=a5702c10-75a7-439c-8b81-199ddd3af62f&fld_title=更新&fld_content=更新
 * http://localhost:8080/article/find?fld_id=a5702c10-75a7-439c-8b81-199ddd3af62f
 * http://localhost:8080/article/delete?fld_id=a5702c10-75a7-439c-8b81-199ddd3af62f
 */
public class ArticleRouter {
	public static final Logger LOGGER = LoggerFactory.getLogger(ArticleRouter.class);

	private Router router;

	public ArticleRouter(Router router) {
		super();
		this.router = router;
	}

	public void start() {

		find();
		add();
		update();
		delete();
		findAll();
	}

	public void find() {
		router.route("/article/find").blockingHandler(routingContext -> {

			// JsonObject body = routingContext.getBodyAsJson();
			JsonObject body = new JsonObject();
			body.put("fld_id", routingContext.request().getParam("fld_id"));
			routingContext.vertx().eventBus().<JsonObject>send("dao://article/find", body, reply -> {
				if (reply.succeeded()) {
					routingContext.response().putHeader("content-type", "text/plain;charset=utf-8");
					routingContext.response().end(reply.result().body().toBuffer());
				} else {
					LOGGER.info(reply.cause());
					// 异常处理
				}

			});
		});

	}


	/**
	 * JsonObject body = routingContext.getBodyAsJson();
	 * 
	 */
	public void add() {

		router.route("/article/add").blockingHandler(routingContext -> {
			LOGGER.info("进入add方法体" );
			//JsonObject body = routingContext.getBodyAsJson();
			JsonObject body = new JsonObject();
			body.put("fld_id", UUID.randomUUID().toString());
			body.put("fld_title", "测试标题");
	        body.put("fld_content", "测试内容");
	        LOGGER.info(body.toBuffer());
			
			routingContext.vertx().eventBus().<JsonObject>send("dao://article/add", body, reply -> {
				if (reply.succeeded()) {
					LOGGER.info("send方法执行成功");
					routingContext.response().putHeader("content-type", "text/plain;charset=utf-8");
					routingContext.response().end(reply.result().body().toBuffer());
				} else {
					LOGGER.info(reply.cause());
					// 异常处理
				}

			});
		});

	}

	public void update() {
		router.route("/article/update").blockingHandler(routingContext -> {

			// JsonObject body = routingContext.getBodyAsJson();
			JsonObject body = new JsonObject();
			body.put("fld_id", routingContext.request().getParam("fld_id"));
			body.put("fld_title", routingContext.request().getParam("fld_title"));
			body.put("fld_content", routingContext.request().getParam("fld_content"));

			routingContext.vertx().eventBus().<JsonObject>send("dao://article/update", body, reply -> {
				if (reply.succeeded()) {
					routingContext.response().putHeader("content-type", "text/plain;charset=utf-8");
					routingContext.response().end(reply.result().body().toBuffer());
				} else {
					LOGGER.info(reply.cause());
					// 异常处理
				}

			});
		});

	}

	public void delete() {
		router.route("/article/delete").blockingHandler(routingContext -> {

			// JsonObject body = routingContext.getBodyAsJson();
			JsonObject body = new JsonObject();
			body.put("fld_id", routingContext.request().getParam("fld_id"));

			routingContext.vertx().eventBus().<JsonObject>send("dao://article/delete", body, reply -> {
				if (reply.succeeded()) {
					routingContext.response().putHeader("content-type", "text/plain;charset=utf-8");
					routingContext.response().end(reply.result().body().toBuffer());
				} else {
					LOGGER.info(reply.cause());
					// 异常处理
				}

			});
		});

	}

	public void findAll() {
		router.route("/article/findAll").blockingHandler(routingContext -> {

			// JsonObject body = routingContext.getBodyAsJson();
			JsonObject body = new JsonObject();

			routingContext.vertx().eventBus().<JsonObject>send("dao://article/findAll", body, reply -> {
				if (reply.succeeded()) {
					routingContext.response().putHeader("content-type", "text/plain;charset=utf-8");
					routingContext.response().end(reply.result().body().toBuffer());
				} else {
					LOGGER.info(reply.cause());
					// 异常处理
				}

			});
		});

	}

}