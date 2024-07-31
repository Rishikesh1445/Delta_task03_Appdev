from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager

db = SQLAlchemy()
login_manager = LoginManager()

def create_app():
    app = Flask(__name__)
    app.config['SECRET_KEY'] = 'your_secret_key'
    app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:rishi1234@localhost/merit_match'
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

    db.init_app(app)
    login_manager.init_app(app)
    login_manager.login_view = 'auth.login'

    from .routes.auth import auth as auth_blueprint
    app.register_blueprint(auth_blueprint)

    from .routes.newtask import newtask as newtask_blueprint
    app.register_blueprint(newtask_blueprint)

    from .routes.taskstatus import taskstatus as taskstatus_blueprint
    app.register_blueprint(taskstatus_blueprint)

    from .routes.fetchtask import fetchtask as fetchtask_blueprint
    app.register_blueprint(fetchtask_blueprint)

    from .routes.home import home as home_blueprint
    app.register_blueprint(home_blueprint)

    from .routes.others import others as others_blueprint
    app.register_blueprint(others_blueprint)

    return app
