Hello, dear ${userName}!

User ${inviterName} invites you in game <#if switchMoveOrder>with move order switching</#if> with ${movesQuantity} moves, ${rounding}.

<#if invitedUsers?size &gt; 0>
 Also invited:
<#list invitedUsers as invitedUser>
    ${invitedUser}
</#list>
</#if>

To accept or reject the invitation go to https://stockholdergame.com

---
This e-mail was sent automatically by mail system of https://stockholdergame.com
You don't need to answer on it.
