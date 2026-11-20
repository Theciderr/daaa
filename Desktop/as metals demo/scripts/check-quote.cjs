const assert=require('node:assert/strict');
(async()=>{const {validateQuote,buildMessage,whatsappUrl}=await import('../app/quote/message.ts');
const valid={name:'Client',phone:'+91 (89509) 17008',need:'Carved doors',size:'',material:'',message:'Reference & finish\nकृपया सलाह दें'};
assert.equal(Object.keys(validateQuote(valid)).length,0);
const bad=validateQuote({...valid,name:'   ',phone:'abc',need:'Invented service'});assert.ok(bad.name&&bad.phone&&bad.need);
assert.ok(validateQuote({...valid,phone:'1234567890123456'}).phone);
const msg=buildMessage(valid,{code:'AMF-3DR-01',name:'Solstice'},'Door & panel.pdf');assert.match(msg,/AMF-3DR-01/);assert.match(msg,/Approximate size: Not sure yet/);assert.match(msg,/Material: Please advise/);assert.match(msg,/Reference to attach in WhatsApp: Door & panel.pdf/);
assert.equal(new URL(whatsappUrl(msg)).searchParams.get('text'),msg);assert.ok(!buildMessage(valid,null).includes('Design:'));console.log('Quote checks passed: validation, selected design, optional fields, attachment reminder, Unicode/URL round trip.');
})().catch(error=>{console.error(error);process.exitCode=1});
