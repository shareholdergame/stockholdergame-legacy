Здравствуйте, уважаемый ${userName}!

Пользователь ${inviterName} пригласил вас на партию <#if switchMoveOrder>со сменой порядка хода</#if> с количеством ходов ${movesQuantity}, ${rounding}.

<#if invitedUsers?size &gt; 0>
 Также приглашены:
<#list invitedUsers as invitedUser>
    ${invitedUser}
</#list>
</#if>

Чтобы принять или отклонить приглашение зайдите на http://stockholdergame.com

---
Это письмо было отправлено автоматически почтовой системой http://stockholdergame.com
Вам не нужно на него отвечать.
