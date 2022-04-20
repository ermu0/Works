import sys
import frida

js_code = '''
     Java.perform(function(){  
        var hook = Java.use("com.tadu.android.model.json.UserInfoModel");
        hook.isLogin.implementation = function(){
            send('first hook start!');
            return true;
        }
        
        hook.getIsMember.implementation = function(){
            send('second hook start!');
            return 1;
        }
    });
'''


def message(msg, data):
    if msg['type'] == 'send':
        print(f'[*] {msg["payload"]}')
    else:
        print(msg)


if __name__ == '__main__':
    process = frida.get_remote_device().attach('塔读小说免费版')
    script = process.create_script(js_code)
    script.on('message', message)
    print('[*] Running****!!!!')
    script.load()
    sys.stdin.read()
    pass