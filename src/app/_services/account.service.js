"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AccountService = void 0;
var core_1 = require("@angular/core");
var rxjs_1 = require("rxjs");
var operators_1 = require("rxjs/operators");
var environment_1 = require("@environments/environment");
var AccountService = /** @class */ (function () {
    function AccountService(router, http) {
        this.router = router;
        this.http = http;
        this.userSubject = new rxjs_1.BehaviorSubject(JSON.parse(localStorage.getItem('user')));
        this.user = this.userSubject.asObservable();
    }
    Object.defineProperty(AccountService.prototype, "userValue", {
        get: function () {
            return this.userSubject.value;
        },
        enumerable: false,
        configurable: true
    });
    AccountService.prototype.login = function (username, password) {
        var _this = this;
        return this.http.post(environment_1.environment.apiUrl + "/users/authenticate", { username: username, password: password })
            .pipe(operators_1.map(function (user) {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('user', JSON.stringify(user));
            _this.userSubject.next(user);
            return user;
        }));
    };
    AccountService.prototype.logout = function () {
        // remove user from local storage and set current user to null
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/account/login']);
    };
    AccountService.prototype.register = function (user) {
        return this.http.post(environment_1.environment.apiUrl + "/users/register", user);
    };
    AccountService.prototype.getAll = function () {
        return this.http.get(environment_1.environment.apiUrl + "/users");
    };
    AccountService.prototype.getById = function (id) {
        return this.http.get(environment_1.environment.apiUrl + "/users/" + id);
    };
    AccountService.prototype.update = function (id, params) {
        var _this = this;
        return this.http.put(environment_1.environment.apiUrl + "/users/" + id, params)
            .pipe(operators_1.map(function (x) {
            // update stored user if the logged in user updated their own record
            if (id == _this.userValue.id) {
                // update local storage
                var user = __assign(__assign({}, _this.userValue), params);
                localStorage.setItem('user', JSON.stringify(user));
                // publish updated user to subscribers
                _this.userSubject.next(user);
            }
            return x;
        }));
    };
    AccountService.prototype.delete = function (id) {
        var _this = this;
        return this.http.delete(environment_1.environment.apiUrl + "/users/" + id)
            .pipe(operators_1.map(function (x) {
            // auto logout if the logged in user deleted their own record
            if (id == _this.userValue.id) {
                _this.logout();
            }
            return x;
        }));
    };
    AccountService.prototype.getAllUsersInConsole = function () {
        this.http.get("http://localhost:9000/core/user/getAll").subscribe(function (data) {
            console.log(data);
        });
    };
    AccountService = __decorate([
        core_1.Injectable({ providedIn: 'root' })
    ], AccountService);
    return AccountService;
}());
exports.AccountService = AccountService;
