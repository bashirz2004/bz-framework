"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.JwtInterceptor = void 0;
var core_1 = require("@angular/core");
var environment_1 = require("@environments/environment");
var JwtInterceptor = /** @class */ (function () {
    function JwtInterceptor(accountService) {
        this.accountService = accountService;
    }
    JwtInterceptor.prototype.intercept = function (request, next) {
        // add auth header with jwt if user is logged in and request is to the api url
        var user = this.accountService.userValue;
        var isLoggedIn = user && user.token;
        var isApiUrl = request.url.startsWith(environment_1.environment.apiUrl);
        if (isLoggedIn && isApiUrl) {
            request = request.clone({
                setHeaders: {
                    Authorization: "Bearer " + user.token
                }
            });
        }
        return next.handle(request);
    };
    JwtInterceptor = __decorate([
        core_1.Injectable()
    ], JwtInterceptor);
    return JwtInterceptor;
}());
exports.JwtInterceptor = JwtInterceptor;
